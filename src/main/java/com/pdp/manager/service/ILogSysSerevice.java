package com.pdp.manager.service;

import java.util.Date;

/**
 * 日志业务接口
 * @author LIXr
 * @date 20/11/17
 */
public interface ILogSysSerevice {
	
	public void insertLog(String operModule,String operType,String operDataId,String operContent,
			String operResult,String failReason,Integer operUserId,String operUser,
			Date operDate,String memo);
	
}
