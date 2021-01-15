package com.pdp.manager.service;

import java.util.List;
import java.util.Map;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalCase;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.redis.RedisUtil;
import com.pdp.manager.response.PageDataResult;

/**
 * 病例（麻卡）业务实现接口
 * @author LIXr
 * @date 20/11/25
 */
public interface IMedicalCaseService {

	public List<MedicalCase> init();
	
	public PageDataResult getList(SearchDTO searchDTO, Integer pageNum, Integer pageSize);
	
    public Map<String,Object> setData(MedicalCase mcase,RedisUtil redisService,MedicalOrgUser user);
    
    public Map<String, Object> referral(Integer dataId,String referralReason,String hospCode,
    		String hospName,RedisUtil redisService,MedicalOrgUser user);
    
    public Map<String, Object> cancellationData(Integer dataId,String cancellationReason,
    		RedisUtil redisService,MedicalOrgUser user);
    
    public MedicalCase getMedicalCase(String patientName,String patientCardid);
    
    public PageDataResult getCaseListByOrgCode(String orgCode, Integer pageNum, Integer pageSize);
    
    public void autoCancellation();
}
