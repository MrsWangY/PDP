package com.pdp.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pdp.manager.pojo.RecipelDrug;


@Repository
public interface RecipelDrugMapper{
	
	public int deleteByPrimaryKey(@Param("id") Integer id);

	public int insert(RecipelDrug record);

	public RecipelDrug selectByPrimaryKey(@Param("id") Integer id);

	public List<RecipelDrug> selectAll();
	
	public List<RecipelDrug> getRecipelDrugListByCardId(@Param("patientIdcard") String patientIdcard);
}
