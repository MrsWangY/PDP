package com.pdp.manager.dao;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalOrgUser;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalOrgUserMapper {
   
	public int deleteByPrimaryKey(@Param("user_id") Integer user_id);

	public int insert(MedicalOrgUser record);

    public List<MedicalOrgUser> selectAll();

    public int updateByPrimaryKey(MedicalOrgUser record);
    
    
    public MedicalOrgUser findByLoginName(@Param("loginName")String loginName);
    
    public MedicalOrgUser getUserByKeyCode(@Param("CACode")String CACode);
    
    public List<MedicalOrgUser> getUserList(SearchDTO searchDTO);
    
    public MedicalOrgUser selectByPrimaryKey(@Param("userId") Integer userId);
    
    public int updateUser(MedicalOrgUser user);
    
    public int updateUserStatus(@Param("userId") Integer userId,@Param("isValid") Integer isValid);
    
    public int resetpw(@Param("userId") Integer userId,@Param("loginPwd")String loginPwd); 
}