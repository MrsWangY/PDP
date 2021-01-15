package com.pdp.manager.service;

import java.util.List;
import java.util.Map;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalOrg;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;

public interface IMedicalOrgService {

	public PageDataResult getList(SearchDTO searchDTO, Integer pageNum, Integer pageSize);
	
	public List<MedicalOrg> getAllList();
	
    public Map<String,Object> setData(MedicalOrg org,MedicalOrgUser user);
    
    public Map<String, Object> deleteData(String orgCode,MedicalOrgUser user);
}
