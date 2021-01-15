package com.pdp.manager.service;

import java.util.Date;
import java.util.List;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.Recipel;
import com.pdp.manager.pojo.RecipelDrug;
import com.pdp.manager.response.PageDataResult;

/**
 * 处方业务接口
 * @author LIXr
 * @date 20/11/17
 */
public interface IRecipelSerevice {
	
	/**
	 * 更新处方标记为有效处方
	 * @param recipeNo 处方号（机构代码+处方号）
	 * @param reason 开药理由
	 */
	public void validRecipel(String recipeNo,String reason);
	
	/**
	 * 更新处方记录的取药相关信息
	 * @param recipeNo 处方号（机构代码+处方号）
	 * @param recipelDispensingDoctNo 发药人编码
	 * @param recipelDispensingDoctName 发药人
	 * @param recipelDispensingDate 发药时间
	 */
	public void updateDispensingInfo(String recipeNo,String recipelDispensingDoctNo,
			String recipelDispensingDoctName,Date recipelDispensingDate);
	
	public Recipel getRecipelInfo(String patientIdcard);
	
	public void  saveRecord(Recipel recipel,List<RecipelDrug> rdList) throws Exception;
	
	//查询就诊记录
	public PageDataResult getRecipelRecord(SearchDTO searchDTO, Integer pageNum, Integer pageSize);
	
}
