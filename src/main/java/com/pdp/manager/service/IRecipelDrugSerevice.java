package com.pdp.manager.service;

import java.util.List;

import com.pdp.manager.pojo.PsychotropicDrugDict;
import com.pdp.manager.pojo.RecipelDrug;

/**
 * 处方内药品业务接口
 * @author LIXr
 * @date 20/11/17
 */
public interface IRecipelDrugSerevice {
	
	public List<PsychotropicDrugDict> init();
	
	public List<RecipelDrug> getRecipelDrugListByCardId(String patientIdcard);
}
