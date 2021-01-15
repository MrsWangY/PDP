package com.pdp.manager.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdp.manager.common.utils.Constants;
import com.pdp.manager.common.utils.DateUtils;
import com.pdp.manager.common.utils.StringHelpers;
import com.pdp.manager.dao.LogInfoMapper;
import com.pdp.manager.dao.LogSysMapper;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.LogInfo;
import com.pdp.manager.pojo.LogSys;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.ILogInfoSerevice;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志业务实现service
 * @author LIXr
 * @date 20/11/17
 */
@Slf4j
@Service
public class LogInfoSereviceImpl implements ILogInfoSerevice{
	
	@Autowired
	private LogInfoMapper logInfoMapper;
	@Autowired
	private LogSysMapper logSysMapper;
	
	@Override
	public PageDataResult getList(SearchDTO searchDTO, Integer pageNum, Integer pageSize){
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<LogInfo> dataList = logInfoMapper.getList(searchDTO);
        if(dataList.size() != 0){
            PageInfo<LogInfo> pageInfo = new PageInfo<>(dataList);
            for(LogInfo data:dataList){
	        	data.setOperDateStr(DateUtils.dateToString(data.getOperDate()));
            }
            pageDataResult.setList(dataList);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;
    }
	
	@Override
	public void insertLog(Integer dataId,String recipeNo,String operType,String operResult,String failReason,
			Date operDate,String memo){
		
		LogInfo loginfo = new LogInfo();
		loginfo.setDataId(dataId);
		loginfo.setRecipeNo(recipeNo);
		loginfo.setOperType(operType);
		loginfo.setOperResult(operResult);
		loginfo.setFailReason(failReason);
		loginfo.setOperDate(operDate);
		loginfo.setMemo(memo);
		logInfoMapper.insert(loginfo);
	}
	
	@Override
	public Map<String, Object> alertData(String recipeNo,MedicalOrgUser user){
		Map<String, Object> data = new HashMap<String, Object>();
        try {
        	if(StringHelpers.isNotEmpty(recipeNo)){
        		int result = logInfoMapper.alertData(recipeNo);
                if(result == 0){
                    data.put("code",Constants.CODE_FAILED);
                    data.put("msg",Constants.FAILED);
                    return data;
                }
        	}
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            
            logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_LOG, Constants.LOG_OPER_ALERT,
            		recipeNo, null, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
       		   new Date(), null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("预警异常！", e);
        }
        return data;
	}
	
	public Map<String, Object> unAlertData(String recipeNo,MedicalOrgUser user){
		Map<String, Object> data = new HashMap<String, Object>();
        try {
        	if(StringHelpers.isNotEmpty(recipeNo)){
        		int result = logInfoMapper.unAlertData(recipeNo);
                if(result == 0){
                    data.put("code",Constants.CODE_FAILED);
                    data.put("msg",Constants.FAILED);
                    return data;
                }
        	}
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            
            logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_LOG, Constants.LOG_OPER_UNALERT,
            		recipeNo, null, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
       		   new Date(), null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("预警异常！", e);
        }
        return data;
	}
}
