package com.pdp.manager.service;

import java.util.Map;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.pojo.PsychotropicDrugDict;
import com.pdp.manager.redis.RedisUtil;
import com.pdp.manager.response.PageDataResult;

/**
 * 药品字典目录相关业务实现接口
 * @author LIXr
 *
 */
public interface IDrugDictService {

	public PageDataResult getList(SearchDTO searchDTO,Integer pageNum,Integer pageSize);
	
	public Map<String,Object> setData(MedicalOrgUser user,PsychotropicDrugDict pd,RedisUtil redisService);
	
	public Map<String,Object> deleteData(Integer dataId,RedisUtil redisService,MedicalOrgUser user);
}
