package com.pdp.manager.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdp.manager.dao.PsychotropicDrugDictMapper;
import com.pdp.manager.dao.RecipelDrugMapper;
import com.pdp.manager.pojo.PsychotropicDrugDict;
import com.pdp.manager.pojo.RecipelDrug;
import com.pdp.manager.service.IRecipelDrugSerevice;
/**
 * 处方内药品业务实现service
 * @author LIXr
 * @date 20/11/17
 */
@Service
public class RecipelDrugSereviceImpl implements IRecipelDrugSerevice{
	
	@Autowired
	private RecipelDrugMapper recipelDrugMapper;
	
	@Autowired
	private PsychotropicDrugDictMapper psychotropicDrugDictMapper;
	
	@Override
	public List<PsychotropicDrugDict> init(){
		return psychotropicDrugDictMapper.selectAll();
	}
	
	@Override
	public List<RecipelDrug> getRecipelDrugListByCardId(String patientIdcard){
		return recipelDrugMapper.getRecipelDrugListByCardId(patientIdcard);
	}
}
