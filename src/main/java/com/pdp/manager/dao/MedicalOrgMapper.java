package com.pdp.manager.dao;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalOrg;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalOrgMapper {
	public int deleteByPrimaryKey(@Param("orgCode")String orgCode);

	public int insert(MedicalOrg record);

	public  MedicalOrg selectByPrimaryKey(@Param("orgCode")String orgCode);

	public List<MedicalOrg> selectAll();

	public  int updateByPrimaryKey(MedicalOrg record);
	
	public List<MedicalOrg> getList(SearchDTO searchDTO);
	
	public int updateData(MedicalOrg record);
	
	public int updateStatus(@Param("orgCode")String orgCode,@Param("isValid") Integer isValid);
}