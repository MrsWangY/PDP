package com.pdp.manager.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.Recipel;


@Repository
public interface RecipelMapper{
	
	public int insert(Recipel recipel);
	/**
	 * 有效化处方记录
	 * @param recipeNo 处方号（机构代码+处方号）
	 */
	public void validRecipel(@Param("recipeNo") String recipeNo,@Param("reason") String reason);
	
	/**
	 * 完成取药，更新处方记录
	 * @param recipeNo
	 * @param recipelDispensingDoctNo
	 * @param recipelDispensingDoctName
	 * @param recipelDispensingDate
	 */
	public void updateDispensingInfo(@Param("recipeNo") String recipeNo,@Param("recipelDispensingDoctNo") String recipelDispensingDoctNo,
			@Param("recipelDispensingDoctName") String recipelDispensingDoctName,
			@Param("recipelDispensingDate") Date recipelDispensingDate);
	
	public Recipel getRecipelInfo(@Param("patientIdcard") String patientIdcard);
	
	public List<Recipel> getRecipelRecord(SearchDTO searchDTO);
}
