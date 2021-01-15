package com.pdp.manager.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdp.manager.dao.LogSysMapper;
import com.pdp.manager.pojo.LogSys;
import com.pdp.manager.service.ILogSysSerevice;

/**
 * 日志业务实现service
 * @author LIXr
 * @date 20/11/17
 */
@Service
public class LogSysSereviceImpl implements ILogSysSerevice{
	
	@Autowired
	private LogSysMapper logSysMapper;
	
	@Override
	public void insertLog(String operModule,String operType,String operDataId,String operContent,
			String operResult,String failReason,Integer operUserId,String operUser,
			Date operDate,String memo){
		
		LogSys log = new LogSys();
		log.setOperModule(operModule);
		log.setOperType(operType);
		log.setOperDataId(operDataId);
		log.setOperContent(operContent);
		log.setOperResult(operResult);
		log.setFailReason(failReason);
		log.setOperUserId(operUserId);
		log.setOperUser(operUser);
		log.setOperDate(operDate);
		log.setMemo(memo);
		logSysMapper.insert(log);
	}
	
}
