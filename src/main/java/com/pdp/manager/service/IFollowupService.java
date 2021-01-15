package com.pdp.manager.service;

import java.util.Map;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.FollowupRecord;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;

/**
 * 随访业务实现接口
 * @author LIXr
 *
 */
public interface IFollowupService {

	public PageDataResult getWarnList(SearchDTO searchDTO, Integer pageNum, Integer pageSize);
	
	public PageDataResult getList(SearchDTO searchDTO, Integer pageNum, Integer pageSize);
	
    public Map<String,Object> setData(MedicalOrgUser user,FollowupRecord rec);
    
    public Map<String, Object> deleteData(Integer id,MedicalOrgUser user);
}
