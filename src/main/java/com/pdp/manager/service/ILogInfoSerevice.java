package com.pdp.manager.service;

import java.util.Date;
import java.util.Map;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;

/**
 * 日志业务接口
 * @author LIXr
 * @date 20/11/17
 */
public interface ILogInfoSerevice {
	
	public PageDataResult getList(SearchDTO searchDTO, Integer pageNum, Integer pageSize);
	
	public void insertLog(Integer dataId,String recipeNo,String operType,String operResult,String failReason,
			Date operDate,String memo);
	
	public Map<String, Object> alertData(String recipeNo,MedicalOrgUser user);
	
	public Map<String, Object> unAlertData(String recipeNo,MedicalOrgUser user);
}
