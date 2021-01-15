package com.pdp.manager.dao;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalCase;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalCaseMapper {
    
	public int deleteByPrimaryKey(@Param("dataId") Integer data_id);

	public int insert(MedicalCase record);

	public MedicalCase selectByPrimaryKey(@Param("dataId") Integer data_id);

	public List<MedicalCase> selectAll();

	public int updateByPrimaryKey(MedicalCase record);
	
	public List<MedicalCase> getList(SearchDTO searchDTO);
	
	public List<MedicalCase> getListByOrgCode(@Param("hospCode") String hospCode);
	
	public int referral(@Param("dataId") Integer dataId,@Param("referralReason")String referralReason);
	
	public int cancellationData(MedicalCase mc);
	
	public MedicalCase isExist(@Param("patientCardid")String patientCardid);
	
	public int updateData(MedicalCase record);
	
	public MedicalCase getMedicalCase(@Param("patientName")String patientName,@Param("patientCardid")String patientCardid);
	
	public List<MedicalCase> redisAll();
	
	public void autoCancellation();
}