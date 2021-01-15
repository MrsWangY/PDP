package com.pdp.manager.webservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.pdp.manager.common.IStatusMessage;
import com.pdp.manager.common.utils.Constants;
import com.pdp.manager.common.utils.DateUtils;
import com.pdp.manager.common.utils.StringHelpers;
import com.pdp.manager.service.ILogInfoSerevice;
import com.pdp.manager.service.IRecipelSerevice;

import java.util.Date;

import javax.jws.WebService;

@WebService(serviceName = "WebUserService",
        targetNamespace="http://webservice.pdp.com/",
        endpointInterface = "com.pdp.manager.webservice.IWebUserService")
@Component
public class WebUserServiceImpl implements IWebUserService {

	@Autowired
	private IRecipelSerevice recipelSerevice;
	@Autowired
	private ILogInfoSerevice logInfoSerevice;
	
	@Override
	public String verifyRecipel(String jsonData){
		
		String code,msg,oprtType=Constants.LOG_OPER_TYPE_Y,operResult,failReason="";
		try {
			//TODO 1.解析出处方及处方药品数据
//			JSONObject rootObject = new JSONObject(jsonData);
//			JSONObject recipel = rootObject.getJSONObject("recipel");
//			JSONArray rdLisJSONObject rootObject = new JSONObject(jsonData);
//			JSONObject recipel = rootObject.getJSONObject("recipel");
//			JSONArrat = rootObject.getJSONArray("rdList");
			
			//TODO 2.精二类药品患者是否存在有效期内的处方药品记录
//			特殊药品 麻醉 精一类药品的吗卡患者 在其他医院开过药  以及吗卡是否已注销
			
			
			//TODO 3.药品与药品字典表对比，有无药品超过规定的用法用量
			
			//TODO 4.插入处方记录 取药状态未完成
			
			//TODO 5.删掉之前同处方的无效记录（验证过程留存的垃圾数据）
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@Override
	public String insertRecipel(String orgCode,String recipeNo,String reason){
		
		String code,msg,oprtType=Constants.LOG_OPER_TYPE_Q,operResult,failReason="";
		recipeNo = orgCode+"_"+recipeNo;
		if(StringHelpers.isNotEmpty(orgCode) && StringHelpers.isNotEmpty(recipeNo)){
			
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
		logInfoSerevice.insertLog(0,recipeNo,oprtType,operResult,failReason,new Date(),"");
		
		 //返回信息
	    JSONObject result = new JSONObject();
	    result.put("code", code);
	    result.put("msg", msg);
	    return result.toJSONString();
     }
	
	@Override
	public String updateDispensingInfo(String orgCode,String recipeNo,String recipelDispensingDoctNo,
			String recipelDispensingDoctName,String recipelDispensingDate){
		
		String code,msg,oprtType=Constants.LOG_OPER_TYPE_M,operResult,failReason="";
		try {
			recipeNo = orgCode+"_"+recipeNo;
			if(StringHelpers.isNotEmpty(orgCode) && StringHelpers.isNotEmpty(recipeNo)
					&& StringHelpers.isNotEmpty(recipelDispensingDoctNo)
					&& StringHelpers.isNotEmpty(recipelDispensingDoctName)
					&& StringHelpers.isNotEmpty(recipelDispensingDate)){
				Date rdDate = null;
				
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
		logInfoSerevice.insertLog(0,recipeNo,oprtType,operResult,failReason,new Date(),"");
				
		 //返回信息
	    JSONObject result = new JSONObject();
	    result.put("code", code);
	    result.put("msg", msg);
	    return result.toJSONString();
	}
	
}