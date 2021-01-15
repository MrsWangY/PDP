package com.pdp.manager.service;

import java.util.Map;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;
/**
 * 医疗机构管理人员业务实现接口
 * @author LIXr
 *
 */
public interface IMedicalOrgUserService {

	public MedicalOrgUser findByLoginName(String loginName);
	
	public MedicalOrgUser getUserByKeyCode(String CACode);
	
	public PageDataResult getUserList(SearchDTO searchDTO, Integer pageNum, Integer pageSize);
	
    public Map<String,Object> addUser(MedicalOrgUser user,MedicalOrgUser loginUser);

    public Map<String,Object> updateUser(MedicalOrgUser user,MedicalOrgUser loginUser);
    
    public Map<String, Object> updateUserStatus(Integer userId,Integer status,MedicalOrgUser user);
    
    public Map<String, Object> resetpw(Integer userId,MedicalOrgUser user);
    
    public Map<String, Object> modifyPW(Integer userId,String oldPassword,String newPassword,MedicalOrgUser user);
}
