package com.pdp.manager.dao;

import com.pdp.manager.pojo.MedicalRecord;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordMapper {
	public  int deleteByPrimaryKey(Integer data_id);

	public int insert(MedicalRecord record);

	public MedicalRecord selectByPrimaryKey(Integer data_id);

	public List<MedicalRecord> selectAll();

	public  int updateByPrimaryKey(MedicalRecord record);
}