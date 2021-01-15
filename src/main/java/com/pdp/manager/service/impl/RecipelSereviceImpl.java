package com.pdp.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdp.manager.common.utils.DateUtils;
import com.pdp.manager.dao.RecipelDrugMapper;
import com.pdp.manager.dao.RecipelMapper;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.Recipel;
import com.pdp.manager.pojo.RecipelDrug;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IRecipelSerevice;

import lombok.extern.slf4j.Slf4j;
/**
 * 处方业务实现service
 * @author LIXr
 * @date 20/11/17
 */
@Slf4j
@Service
public class RecipelSereviceImpl implements IRecipelSerevice{
	
	@Autowired
    private RecipelMapper recipelMapper;
	@Autowired
	private RecipelDrugMapper recipelDrugMapper;
	
	public void validRecipel(String recipeNo,String reason){
		recipelMapper.validRecipel(recipeNo,reason);
	}
	
	public void updateDispensingInfo(String recipeNo,String recipelDispensingDoctNo,
			String recipelDispensingDoctName,Date recipelDispensingDate){
		recipelMapper.updateDispensingInfo(recipeNo,recipelDispensingDoctNo,recipelDispensingDoctName,recipelDispensingDate);
	}
	
	public Recipel getRecipelInfo(String patientIdcard){
		return recipelMapper.getRecipelInfo(patientIdcard);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void  saveRecord(Recipel recipel,List<RecipelDrug> rdList)  throws Exception{
		//会自动回滚
		try{
			int insertNum = recipelMapper.insert(recipel);
//			log.info("dataId"+recipel.getDataId());
			int dataId = recipel.getDataId();
			for(RecipelDrug rd:rdList){
				rd.setDataId(dataId);
				rd.setRecipeNo(recipel.getRecipeNo());
				recipelDrugMapper.insert(rd);
			}
		}catch (Exception e){
	        log.info("异常了=====" + e);
//	        e.printStackTrace();
	        //手动强制回滚事务，这里一定要第一时间处理
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    }
	}
	
	public PageDataResult getRecipelRecord(SearchDTO searchDTO, Integer pageNum, Integer pageSize){
		PageDataResult pageDataResult = new PageDataResult();
		PageHelper.startPage(pageNum, pageSize);
		 
	    List<Recipel> dataList = recipelMapper.getRecipelRecord(searchDTO);
	
	    if(dataList.size() != 0){
	        PageInfo<Recipel> pageInfo = new PageInfo<>(dataList);
	        for(Recipel data:dataList){
	        	data.setPatientBdStr(DateUtils.dateToDateString(data.getPatientBd()));
	        	data.setRecipelBuildDateStr(DateUtils.dateToString(data.getRecipelBuildDate()));
	        	data.setRecipelDispensingDateStr(DateUtils.dateToString(data.getRecipelDispensingDate()));
	        	data.setOperateDateStr(DateUtils.dateToString(data.getOperateDate()));
	        	data.setModifyDateStr(DateUtils.dateToString(data.getModifyDate()));
	        }
	        pageDataResult.setList(dataList);
	        pageDataResult.setTotals((int) pageInfo.getTotal());
	    }
	    return pageDataResult;
	}
}
