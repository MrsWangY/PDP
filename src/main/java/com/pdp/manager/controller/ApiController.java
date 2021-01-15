package com.pdp.manager.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pdp.manager.common.IStatusMessage;
import com.pdp.manager.common.utils.Constants;
import com.pdp.manager.common.utils.DateUtils;
import com.pdp.manager.common.utils.JwtUtil;
import com.pdp.manager.common.utils.StringHelpers;
import com.pdp.manager.dto.ParamDTO;
import com.pdp.manager.dto.ReturnDTO;
import com.pdp.manager.pojo.MedicalCase;
import com.pdp.manager.pojo.PsychotropicDrugDict;
import com.pdp.manager.pojo.Recipel;
import com.pdp.manager.pojo.RecipelDrug;
import com.pdp.manager.redis.RedisUtil;
import com.pdp.manager.result.Result;
import com.pdp.manager.result.ResultMsg;
import com.pdp.manager.service.ILogInfoSerevice;
import com.pdp.manager.service.IMedicalCaseService;
import com.pdp.manager.service.IRecipelDrugSerevice;
import com.pdp.manager.service.IRecipelSerevice;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@Slf4j
@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private IRecipelSerevice recipelSerevice;
	@Autowired
	private ILogInfoSerevice logInfoSerevice;
	@Autowired
    private IMedicalCaseService medicalCaseService; //麻卡业务实现类
	@Autowired
	private IRecipelDrugSerevice recipelDrugSerevice;
	
	@Autowired
    private RedisUtil redisService;
    /**
     * api 登录
     * @param userName
     * @param Password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public Result login(HttpServletResponse response, @RequestParam("loginName") String userName, @RequestParam("loginPwd") String Password) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, Password);
        //登录
        currentUser.login(token);
        response.setHeader("xToken", JwtUtil.createJWT(userName,"subject",60*60*1000));
        return Result.genSuccessResult();
    }

    /**
     * 接口鉴权
     * @param userName
     * @param Password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getToken", method = {RequestMethod.POST})
    @ResponseBody
    public ResultMsg getToken(HttpServletResponse response, @RequestParam("username") String userName, @RequestParam("password") String Password) throws Exception {
        
    	Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, Password);
        //登录
		try {
			currentUser.login(token);
			String xToken = JwtUtil.createJWT(userName,"subject",60*1000);
			return new ResultMsg("10000","SUCCESS",xToken);
		} catch (Exception e) {
//			e.printStackTrace();
			return new ResultMsg("9999","NO_TOKEN","");
		}
    }

//    @ExceptionHandler(Exception.class)
//    public Result handleException(Exception e) {
//        e.printStackTrace();
//        return new Result("456","123",new ReturnDTO());
//    }
    
    /**
     * 验证处方药品
     * @param jsonData
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verifyRecipel", method = {RequestMethod.POST}, consumes = "application/json")
    public Result verifyRecipel(@RequestBody ParamDTO paramDTO){
    	log.info("-----------------"+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
    	String recipeNo = "",code= "",msg= "",oprtType=Constants.LOG_OPER_TYPE_Y,operResult= "",failReason="";
//    	String data="";
    	ReturnDTO data = new ReturnDTO();
    	int dataId = 0; //处方记录的主键id
    	boolean redisFlag = true,flag = true,operFlag = true,paramFlag = true,
    			noDoneFlag = true,drugCFFlag = true,drugUseCFFlag = true, successFlag = false;
		try {
			//redis内麻卡记录、药品字典记录
	        Map hash=redisService.getHash("redisDB");
	        List<MedicalCase> mcList = null;
	        List<PsychotropicDrugDict> drugList = null;
	        if (hash!=null){
	        	mcList = (List<MedicalCase>)hash.get("mcList");
	        	drugList = (List<PsychotropicDrugDict>)hash.get("drugList");
	        	if(mcList==null || mcList.size()==0 || drugList==null || drugList.size()==0)
	            	redisFlag = false;
	        }else{
	        	log.info("redis空了...");
	        	redisFlag = false;
	        }
	        
			if(paramDTO!=null && paramDTO.getRecipel()!=null && paramDTO.getRdList()!=null && paramDTO.getRdList().size()!=0){
				//1.解析出处方及处方药品数据
				Recipel recipel = paramDTO.getRecipel();
				List<RecipelDrug> rdList = paramDTO.getRdList();
				
				//确定主要参数
				if(recipel!=null && StringHelpers.isNotEmpty(recipel.getOrgCode()) && StringHelpers.isNotEmpty(recipel.getRecipeNo())
						&& StringHelpers.isNotEmpty(recipel.getPatientName()) && StringHelpers.isNotEmpty(recipel.getPatientIdcard())){
					
					recipeNo = recipel.getOrgCode().trim()+"_"+recipel.getRecipeNo().trim();
					
					/**
					 * 药品是否含有麻醉/精一类， 如果有获取是否麻卡登记，没有登记【返回-联系医院管理员维护麻卡记录】【系统维护】
					 * 如有登记后，判断是否在供药医疗机构开药，不在供药医疗机构【返回-麻卡归属地是哪里，不能在此医疗机构开药】【重复开药】【记录无效数据-日志】
					 * 在供药医疗机构，查询最近的处方记录，锁定3个月内不能重复开药【返回-提示还有几天才能开药】【重复开药】【记录无效数据-日志】
					 * 
					 * 药品目录：药品名称、剂型、患者分类、最多日/次用量
					 * 处方药品，同药品同剂型同患者分类，用量大于规定量【返回-超出规定用量】【超出用量】【记录无效数据-日志】
					 * （药品目录：麻醉、精一类药品针剂3天，普通（其他）剂7天，缓控释制剂15天）
					 * 
					 * 药品是否含有精二类，如果有，先判断医疗机构是否上报开药的资质，没有【返回-该医疗机构没有开药资质，如果有联系医院管理员上报】【系统维护】
					 * 查询最近处方记录，是否有药品还在7天内，在【返回-提示还有几天才能开哪个药】【重复开药】【记录无效数据-日志】
					 * 处方内药品7天内，不能开相同用途的其他药品，【返回-提示相同用途的哪个药还有几天量】【重复开药】【记录无效数据-日志】
					 * 
					 * （药品目录：精二类最多日/次用量 7天）
					 * 处方药品用量大于7天【返回-超出规定用量】【超出用量】【记录无效数据-日志】
					 * 
					 * 处方药品没有对应的药品目录？？？暂时不对应
					 * 记录有效数据 //无效化上次开错、未交费取药的处方记录
					 */
					
					//分类处方内药品
					boolean [] YFlag = new boolean[1],EFlag = new boolean[1];
					boolean [] isOver = new boolean[1];
					String [] overDrugName = new String[1];
					
					if(!redisFlag){ //redis未获取到药品目录
						log.info("redis未获取到药品目录...");
						drugList = recipelDrugSerevice.init();
					}
					// 遍历药品
		        	rdList.stream().forEach(
		        			item->{ 
		        				if(Constants.DRUG_TYPE_MZ.equals(((RecipelDrug)item).getDrugType())
		        					|| Constants.DRUG_TYPE_JYL.equals(((RecipelDrug)item).getDrugType())){
		        					YFlag[0] = true;
		        					if(Constants.DRUG_CLASS_ZSJ.equals(((RecipelDrug)item).getDrugClass()) 
		        							&& ((RecipelDrug)item).getMaxDays()>Constants.MAX_DAY_3){
		        						isOver[0] = true;
		        					}else if(Constants.DRUG_CLASS_HSJ.equals(((RecipelDrug)item).getDrugClass()) 
		        							&& ((RecipelDrug)item).getMaxDays()>Constants.MAX_DAY_15){
		        						isOver[0] = true;
		        					}else if(Constants.DRUG_CLASS_QT.equals(((RecipelDrug)item).getDrugClass()) 
		        							&& ((RecipelDrug)item).getMaxDays()>Constants.MAX_DAY_7){
		        						isOver[0] = true;
		        					}
		        		        }else if(Constants.DRUG_TYPE_JEL.equals(((RecipelDrug)item).getDrugType())){
		        		        	EFlag[0] = true;
		        		        	if(((RecipelDrug)item).getMaxDays()>Constants.MAX_DAY_7){
		        						isOver[0] = true;
		        					}
		        		        }
		        				if(isOver[0])
		        					overDrugName[0] = ((RecipelDrug)item).getDrugChnName();
		        			});
		        	if(isOver[0]){ //药品超出规定用量
		        		operFlag = false;
						code = IStatusMessage.SystemStatus.FAIL_CCYL.getCode();
						msg = IStatusMessage.SystemStatus.FAIL_CCYL.getMessage();
//						data = overDrugName[0]; 
						data = new ReturnDTO(overDrugName[0],null);
						operResult = Constants.FAILED;
						failReason = Constants.LOG_FAIL_REASON_CCYL;
		        	}else{
//		        		Recipel rc = null; //患者最近的开药记录
		        		List<RecipelDrug> rdL = null; //患者最开的开药药品列表
		        		if(YFlag[0]){ //含麻醉、精一类
								
							//姓名和身份证号获取麻卡记录
		        			MedicalCase mc = null;
		        			if(!redisFlag){ //redis未获取到麻卡目录
								log.info("redis未获取到麻卡目录...");
								mc = medicalCaseService.getMedicalCase(recipel.getPatientName().trim(),recipel.getPatientIdcard().trim());
							}else{
								for (MedicalCase s :mcList) {
									if(s!=null && recipel.getPatientName().trim().equals(s.getPatientName())
											&& recipel.getPatientIdcard().trim().equals(s.getPatientCardid())){
										mc = s;
										break;
									}
								}
							}
		        			if(mc != null && StringHelpers.isNotEmpty(mc.getHospCode().trim())){
									
								if(recipel.getOrgCode().trim().equals(mc.getHospCode().trim())){
									
//									rc = recipelSerevice.getRecipelInfo(recipel.getPatientIdcard().trim());
									rdL = recipelDrugSerevice.getRecipelDrugListByCardId(recipel.getPatientIdcard().trim());
									if(rdL!=null && rdL.size()!=0){ //存在历史处方记录，不是第一次开药
										if(((RecipelDrug)rdL.get(0)).getIsDone()==0){ //未取药
											noDoneFlag = false;
										}else{
											//rdList传入药品列表   rdL最近处方药品列表
											for (RecipelDrug rdo :rdL) {  //
												for(RecipelDrug rdn :rdList){
													if(rdo.getDrugCode()!=null && rdo.getDrugCode().equals(rdn.getDrugCode())
															&& rdo.getDrugClass()!=null && rdo.getDrugClass().equals(rdn.getDrugClass())
															&& rdo.getDiffDays()!=null && rdo.getDiffDays()>0){ //存在未使用完的药品
														drugCFFlag = false;
														data = new ReturnDTO(rdo.getDrugChnName(),rdo.getDiffDays());
//														data = rdo.getDiffDays()+"";
														break;
													}
												}
											}
											if(drugCFFlag)
												successFlag = true;
										}
									}else
										successFlag = true;
								}else{
									operFlag = false;
									code = IStatusMessage.SystemStatus.FAIL_ORG_CF.getCode();
									msg = IStatusMessage.SystemStatus.FAIL_ORG_CF.getMessage();
//									data = mc.getHospName().trim();
									data = new ReturnDTO(mc.getHospName().trim(),null);
									operResult = Constants.FAILED;
									failReason = Constants.LOG_FAIL_REASON_ORG_CF;
								}
							}else
								flag = false;
						}
						
						if(EFlag[0]){ //含精二类
							
							rdL = recipelDrugSerevice.getRecipelDrugListByCardId(recipel.getPatientIdcard().trim());
							if(rdL!=null && rdL.size()!=0){ //存在历史处方记录，不是第一次开药
								
								if(((RecipelDrug)rdL.get(0)).getIsDone()==0){ //未取药
									noDoneFlag = false;
								}else{
									 
									for (RecipelDrug rdo :rdL) {  //
										for(RecipelDrug rdn :rdList){
											if(rdo.getDrugCode()!=null && rdo.getDrugCode().equals(rdn.getDrugCode())
													&& rdo.getDrugClass().equals(rdn.getDrugClass())
													&& rdo.getDiffDays()!=null && rdo.getDiffDays()>0){ //存在未使用完的药品
												drugCFFlag = false;
												data = new ReturnDTO(rdo.getDrugChnName(),rdo.getDiffDays());
//												data = rdo.getDiffDays()+"";
												break;
											}
										}
									}
									
									if(drugCFFlag){
										for (RecipelDrug rdo :rdL) {  //
											for(RecipelDrug rdn :rdList){
												if(rdo.getDrugUse()!=null && rdo.getDrugUse().equals(rdn.getDrugUse())
														&& rdo.getDiffDays()!=null && rdo.getDiffDays()>0){ //存在同药效未使用完的药品
													drugUseCFFlag = false;
													data = new ReturnDTO(rdo.getDrugChnName(),rdo.getDiffDays());
//													data = rdo.getDiffDays()+"";
													break;
												}
											}
										}
									}
									
									if(drugCFFlag && drugUseCFFlag)
										successFlag = true;
								}
							}else
								successFlag = true;
						}
		        	}
					if(flag){
						
						//插入处方及药品记录 取药状态未完成
						recipel.setIsDone(0);  //取药状态-未取药
						recipel.setIsInsist(0);//强制开药状态-未强制开药
						
						if(successFlag){ //数据是否有效
							recipel.setIsValid(Constants.Y);
						}else{
							recipel.setIsValid(Constants.N);
						}
						recipel.setRecipeNo(recipeNo);
						//保存处方及药品记录
						log.info("save data...");
						recipelSerevice.saveRecord(recipel,rdList);
						log.info("save data success!!!");
						
						if(!noDoneFlag){
							code = IStatusMessage.SystemStatus.FAIL_NO_DONE_CF.getCode();
							msg = IStatusMessage.SystemStatus.FAIL_NO_DONE_CF.getMessage();
							
							operResult = Constants.FAILED;
							failReason = Constants.LOG_FAIL_REASON_NO_DONE_CF;
						}else if(!drugCFFlag){
							code = IStatusMessage.SystemStatus.FAIL_DRUG_CF.getCode();
							msg = IStatusMessage.SystemStatus.FAIL_DRUG_CF.getMessage();
							
							operResult = Constants.FAILED;
							failReason = Constants.LOG_FAIL_REASON_DRUG_CF;
						}else if(!drugUseCFFlag){
							code = IStatusMessage.SystemStatus.FAIL_DRUG_CLASS_CF.getCode();
							msg = IStatusMessage.SystemStatus.FAIL_DRUG_CLASS_CF.getMessage();
							
							operResult = Constants.FAILED;
							failReason = Constants.LOG_FAIL_REASON_DRUG_USE_CF;
						}else if(successFlag){
							code = IStatusMessage.SystemStatus.SUCCESS.getCode();
							msg = IStatusMessage.SystemStatus.SUCCESS.getMessage();
							
							operResult = Constants.SUCCESS;
							failReason = "";
						}
						
					}else{ //联系管理员维护麻卡记录，不可继续开具处方  //TODO 精二类药品需要验证医疗机构是否上报开药资质
						
						code = IStatusMessage.SystemStatus.NO_MK.getCode();
						msg = IStatusMessage.SystemStatus.NO_MK.getMessage();
						
						operResult = Constants.FAILED;
						failReason = Constants.LOG_FAIL_REASON_NO_MK;
					}
				}else{
					paramFlag = false;
				}
			}else{
				paramFlag = false;
			}
			
			if(!paramFlag){
				code = IStatusMessage.SystemStatus.PARAM_ERROR.getCode();
				msg = IStatusMessage.SystemStatus.PARAM_ERROR.getMessage();
				
				operResult = Constants.FAILED;
				failReason = Constants.LOG_FAIL_REASON_CS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			code = IStatusMessage.SystemStatus.ERROR.getCode();
			msg = IStatusMessage.SystemStatus.ERROR.getMessage();
			
			operResult = Constants.FAILED;
			failReason = Constants.LOG_FAIL_REASON_XT;
		}finally{
			
			//保存操作日志
			logInfoSerevice.insertLog(dataId,recipeNo,oprtType,operResult,failReason,new Date(),"");
		}
		log.info("-----------------"+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
		 //返回信息
	    return new Result(code,msg,data);
	}

    /**
     * 强制开药
     * @param response
     * @param orgCode
     * @param recipeNo
     * @param reason
     * @return
     */
    @RequestMapping(value = "/insertRecipel", method = {RequestMethod.POST})
    @ResponseBody
	public Result insertRecipel(HttpServletResponse response, 
			@RequestParam("orgCode") String orgCode,@RequestParam("recipeNo") String recipeNo,
			@RequestParam("reason") String reason){
		
		String code,msg,oprtType=Constants.LOG_OPER_TYPE_Q,operResult,failReason="";
		int dataId = 0; //处方记录的主键id
		if(StringHelpers.isNotEmpty(orgCode) && StringHelpers.isNotEmpty(recipeNo)){
			
			recipeNo = orgCode.trim()+"_"+recipeNo.trim();
			recipelSerevice.validRecipel(recipeNo,reason);
			
			code = IStatusMessage.SystemStatus.SUCCESS.getCode();
			msg = IStatusMessage.SystemStatus.SUCCESS.getMessage();
			
			operResult = Constants.SUCCESS;
		}else{
			code = IStatusMessage.SystemStatus.PARAM_ERROR.getCode();
			msg = IStatusMessage.SystemStatus.PARAM_ERROR.getMessage();
			
			operResult = Constants.FAILED;
			failReason = Constants.LOG_FAIL_REASON_CS;
		}
		
		//保存操作日志
		logInfoSerevice.insertLog(dataId,recipeNo,oprtType,operResult,failReason,new Date(),reason);
		
		 //返回信息
	    return new Result(code,msg);
     }
    
    /**
     * 药房取药
     * @param orgCode
     * @param recipeNo
     * @param recipelDispensingDoctNo
     * @param recipelDispensingDoctName
     * @param recipelDispensingDate
     * @return
     */
    @RequestMapping(value = "/updateDispensingInfo", method = {RequestMethod.POST})
    @ResponseBody
	public Result updateDispensingInfo(@RequestParam("orgCode") String orgCode,
			@RequestParam("recipeNo") String recipeNo,
			@RequestParam("recipelDispensingDoctNo") String recipelDispensingDoctNo,
			@RequestParam("recipelDispensingDoctName") String recipelDispensingDoctName,
			@RequestParam("recipelDispensingDate") String recipelDispensingDate){
		
		String code,msg,oprtType=Constants.LOG_OPER_TYPE_M,operResult,failReason="";
		Date rdDate = null;
		int dataId = 0; //处方记录的主键id
		try {
			if(StringHelpers.isNotEmpty(orgCode) && StringHelpers.isNotEmpty(recipeNo)
					&& StringHelpers.isNotEmpty(recipelDispensingDoctNo)
					&& StringHelpers.isNotEmpty(recipelDispensingDoctName)
					&& StringHelpers.isNotEmpty(recipelDispensingDate)){
				
				recipeNo = orgCode.trim()+"_"+recipeNo.trim();
				
				rdDate = DateUtils.stringToDate(recipelDispensingDate);
				recipelSerevice.updateDispensingInfo(recipeNo,recipelDispensingDoctNo,recipelDispensingDoctName,rdDate);
				
				code = IStatusMessage.SystemStatus.SUCCESS.getCode();
				msg = IStatusMessage.SystemStatus.SUCCESS.getMessage();
				
				operResult = Constants.SUCCESS;
			}else{
				code = IStatusMessage.SystemStatus.PARAM_ERROR.getCode();
				msg = IStatusMessage.SystemStatus.PARAM_ERROR.getMessage();
				
				operResult = Constants.FAILED;
				failReason = Constants.LOG_FAIL_REASON_CS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			code = IStatusMessage.SystemStatus.ERROR.getCode();
			msg = IStatusMessage.SystemStatus.ERROR.getMessage();
			
			operResult = Constants.FAILED;
			failReason = Constants.LOG_FAIL_REASON_XT;
		}
		//保存操作日志
		logInfoSerevice.insertLog(dataId,recipeNo,oprtType,operResult,failReason,new Date(),"");
				
		 //返回信息
		return new Result(code,msg);
	}
}
