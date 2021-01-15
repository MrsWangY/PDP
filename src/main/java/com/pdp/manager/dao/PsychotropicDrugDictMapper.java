package com.pdp.manager.dao;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.PsychotropicDrugDict;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PsychotropicDrugDictMapper {
   
	public int deleteByPrimaryKey(@Param("dataId") Integer dataId);

	public int insert(PsychotropicDrugDict record);

	public PsychotropicDrugDict selectByPrimaryKey(@Param("dataId") Integer dataId);

	public List<PsychotropicDrugDict> selectAll();
	
	public List<PsychotropicDrugDict> getList(SearchDTO searchDTO);

	public int updateByPrimaryKey(PsychotropicDrugDict record);
}