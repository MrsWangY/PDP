package com.pdp.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdp.manager.common.utils.Constants;
import com.pdp.manager.common.utils.DateUtils;
import com.pdp.manager.dao.LogSysMapper;
import com.pdp.manager.dao.MedicalCaseMapper;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.LogSys;
import com.pdp.manager.pojo.MedicalCase;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.redis.RedisUtil;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IMedicalCaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicalCaseServiceImpl implements IMedicalCaseService{

	@Autowired
	private MedicalCaseMapper medicalCaseMapper;
	@Autowired
	private LogSysMapper logSysMapper;

	@Override
	public List<MedicalCase> init(){
		return medicalCaseMapper.redisAll();
	}
	
	@Override
	public PageDataResult getList(SearchDTO searchDTO, Integer pageNum, Integer pageSize){
		PageDataResult pageDataResult = new PageDataResult();
		PageHelper.startPage(pageNum, pageSize);
		 
	    List<MedicalCase> dataList = medicalCaseMapper.getList(searchDTO);
	
	    if(dataList.size() != 0){
	        PageInfo<MedicalCase> pageInfo = new PageInfo<>(dataList);
	        for(MedicalCase data:dataList){
	        	data.setPatientBirthdayStr(DateUtils.dateToDateString(data.getPatientBirthday()));
	        	data.setDiagTimeStr(DateUtils.dateToDateString(data.getDiagTime()));
	        	data.setGiveCardDateStr(DateUtils.dateToString(data.getGiveCardDate()));
	        	data.setFileDateStr(DateUtils.dateToString(data.getFileDate()));
	        	data.setReferralDateStr(DateUtils.dateToString(data.getReferralDate()));
	        	data.setCancellationDateStr(DateUtils.dateToString(data.getCancellationDate()));
	        }
	        pageDataResult.setList(dataList);
	        pageDataResult.setTotals((int) pageInfo.getTotal());
	    }
	    return pageDataResult;
	}
	
	@Override
	public PageDataResult getCaseListByOrgCode(String orgCode, Integer pageNum, Integer pageSize){
		PageDataResult pageDataResult = new PageDataResult();
		PageHelper.startPage(pageNum, pageSize);
		 
	    List<MedicalCase> dataList = medicalCaseMapper.getListByOrgCode(orgCode);
	
	    if(dataList.size() != 0){
	        PageInfo<MedicalCase> pageInfo = new PageInfo<>(dataList);
	        for(MedicalCase data:dataList){
	        	data.setPatientBirthdayStr(DateUtils.dateToDateString(data.getPatientBirthday()));
	        	data.setDiagTimeStr(DateUtils.dateToDateString(data.getDiagTime()));
	        	data.setGiveCardDateStr(DateUtils.dateToString(data.getGiveCardDate()));
	        	data.setFileDateStr(DateUtils.dateToString(data.getFileDate()));
	        	data.setReferralDateStr(DateUtils.dateToString(data.getReferralDate()));
	        	data.setCancellationDateStr(DateUtils.dateToString(data.getCancellationDate()));
	        }
	        pageDataResult.setList(dataList);
	        pageDataResult.setTotals((int) pageInfo.getTotal());
	    }
	    return pageDataResult;
	}
	@Override
	public void autoCancellation(){
		log.info("autoCancellation...");
		//1.距离发卡日期已超3个月，且无随访记录的麻卡记录
		//2.最近随访时间已超3个月的麻卡记录
//		medicalCaseMapper.autoCancellation();
	}
	
    public Map<String,Object> setData(MedicalCase mcase,RedisUtil redisService,MedicalOrgUser user){
    	Map<String,Object> data = new HashMap<String,Object>();
    	Map map=redisService.getHash("redisDB");
        List<MedicalCase> mcList = map==null?null:(List<MedicalCase>)map.get("mcList");
        String operType = "",operContent="";
        Integer operDataId = 0;
        try {
        	int result = 0;
        	if(mcase!=null && mcase.getDataId()==null){
        		operType = Constants.LOG_OPER_NEW;
        		MedicalCase old = medicalCaseMapper.isExist(mcase.getPatientCardid());
                if(old != null){
                	String msg = "该患者的麻卡记录已存在，所属医疗机构：["+old.getHospName()+"]！";
                    data.put("code",Constants.CODE_FAILED);
                    data.put("msg",msg);
                    logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALCASE, operType,
                    		null, null, Constants.FAILED, msg, user.getUserId(), user.getUserName(),
               		   new Date(), null));
                    return data;
                }
                mcase.setFileDate(DateUtils.getCurrentDateToDate());
                mcase.setIsCancellation(Constants.N);
                result = medicalCaseMapper.insert(mcase);
                
                if(mcList==null)  mcList = new ArrayList<MedicalCase>();
                mcList.add(mcase);
                
                operDataId = mcase.getDataId();
                operContent = mcase.getPatientName()+";"+mcase.getPatientCardid()+";"+mcase.getHospName();
        	}else if(mcase!=null){
        		operType = Constants.LOG_OPER_EDIT;
        		result = medicalCaseMapper.updateByPrimaryKey(mcase);
        		
        		for(int i=0;i<mcList.size();i++){
        			if(mcList.get(i).getDataId()==mcase.getDataId()){
        				mcList.set(i, mcase);
        				break;
        			}
        		}
        		operDataId = mcase.getDataId();
        	}
            if(result == 0){
                data.put("code",Constants.CODE_FAILED);
                data.put("msg",Constants.FAILED);
                return data;
            }
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
           
            if(map!=null){
            	log.info("update redis...");
            	 map.put("mcList",mcList); //更新麻卡
    			 //redis中添加hash
    	        redisService.setHash("redisDB",map);
            }else{
            	log.info("redis empty...");
            }
            logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALCASE, operType,
            		operDataId+"", operContent, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
       		   new Date(), null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[登记和更新麻卡信息]异常！", e);
            return data;
        }
        return data;
    }
    
    public Map<String, Object> referral(Integer dataId,String referralReason,String hospCode,
    		String hospName,RedisUtil redisService,MedicalOrgUser user){
    	 Map<String, Object> data = new HashMap<String, Object>();
    	 Map map=redisService.getHash("redisDB");
         List<MedicalCase> mcList = map==null?null:(List<MedicalCase>)map.get("mcList");
         try {
        	 MedicalCase copyData = medicalCaseMapper.selectByPrimaryKey(dataId);
        	 copyData.setHospCode(hospCode);
        	 copyData.setHospName(hospName);
        	 copyData.setGiveCardOrgCode(hospCode);
        	 copyData.setGiveCardOrgName(hospName);
        	 copyData.setReferralDate(DateUtils.getCurrentDateToDate());
        	 copyData.setReferralReason(referralReason);
        	 copyData.setFileDate(DateUtils.getCurrentDateToDate());
        	 copyData.setDataId(null);
        	 //先注销后新增
        	 int result = medicalCaseMapper.referral(dataId,referralReason);
             if(result == 0){
                 data.put("code",Constants.CODE_FAILED);
                 data.put("msg",Constants.FAILED);
                 return data;
             }
             result = medicalCaseMapper.insert(copyData);
        	 if(result == 0){
                 data.put("code",Constants.CODE_FAILED);
                 data.put("msg",Constants.FAILED);
                 return data;
             }
             data.put("code",Constants.CODE_SUCCESS);
             data.put("msg",Constants.SUCCESS);
             
             if(map!=null){
            	 for(int i=0;i<mcList.size();i++){
         			if(mcList.get(i).getDataId()==dataId){
         				mcList.remove(i);
         				break;
         			}
         		}
            	mcList.add(copyData);
             	log.info("update redis...");
             	 map.put("mcList",mcList); //更新麻卡
     			 //redis中添加hash
     	        redisService.setHash("redisDB",map);
             }else{
             	log.info("redis empty...");
             }
             String operContent = "转诊后dataId:"+copyData.getDataId()+";理由："+referralReason+";医院："+hospName;
             logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALCASE, Constants.LOG_OPER_REFERRAL,
            		 dataId+"", operContent, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
        		   new Date(), null));
         } catch (Exception e) {
             e.printStackTrace();
             log.error("转诊异常！", e);
         }
         return data;
    }
    
    public Map<String, Object> cancellationData(Integer dataId,String cancellationReason,
    		RedisUtil redisService,MedicalOrgUser user){
    	Map<String, Object> data = new HashMap<String, Object>();
    	Map map=redisService.getHash("redisDB");
        List<MedicalCase> mcList = map==null?null:(List<MedicalCase>)map.get("mcList");
        try {
        	MedicalCase mc = new MedicalCase();
        	mc.setDataId(dataId);
        	mc.setCancellationReason(cancellationReason);
        	mc.setCancellationType("手动注销");
            int result = medicalCaseMapper.cancellationData(mc);
            if(result == 0){
                data.put("code",Constants.CODE_FAILED);
                data.put("msg",Constants.FAILED);
                return data;
            }
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            
            if(map!=null){
            	 for(int i=0;i<mcList.size();i++){
          			if(mcList.get(i).getDataId()==dataId){
          				mcList.remove(i);
          				break;
          			}
          		}
            	log.info("update redis...");
            	 map.put("mcList",mcList); //更新麻卡
    			 //redis中添加hash
    	        redisService.setHash("redisDB",map);
            }else{
            	log.info("redis empty...");
            }
            
            logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALCASE, Constants.LOG_OPER_CANCELLATION,
           		 dataId+"", "理由："+cancellationReason, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
       		   new Date(), null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("注销异常！", e);
        }
        return data;
    }
    
    public MedicalCase getMedicalCase(String patientName,String patientCardid){
    	return medicalCaseMapper.getMedicalCase(patientName,patientCardid);
    }
}
