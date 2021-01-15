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
import com.pdp.manager.dao.FollowupRecordMapper;
import com.pdp.manager.dao.LogSysMapper;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.dto.VWarnFollowDTO;
import com.pdp.manager.pojo.FollowupRecord;
import com.pdp.manager.pojo.LogSys;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IFollowupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FollowupServiceImpl implements IFollowupService{

	@Autowired
	private FollowupRecordMapper followupRecordMapper;
	@Autowired
	private LogSysMapper logSysMapper;
	
	@Override
	public PageDataResult getWarnList(SearchDTO searchDTO, Integer pageNum, Integer pageSize){
		PageDataResult pageDataResult = new PageDataResult();
		PageHelper.startPage(pageNum, pageSize);
		
	    List<VWarnFollowDTO> dataList = followupRecordMapper.selectWarn(searchDTO);
	
	    if(dataList.size() != 0){
	        PageInfo<VWarnFollowDTO> pageInfo = new PageInfo<>(dataList);
	        for(VWarnFollowDTO data:dataList){
	        	data.setBaseDateStr(DateUtils.dateToString(data.getBaseDate()));
	        	data.setNextMonthStr(DateUtils.dateToDateString(data.getNextMonth()));
            }
	        pageDataResult.setList(dataList);
	        pageDataResult.setTotals((int) pageInfo.getTotal());
	    }
	    return pageDataResult;
	}
	
	@Override
	public PageDataResult getList(SearchDTO searchDTO, Integer pageNum, Integer pageSize){
		PageDataResult pageDataResult = new PageDataResult();
		PageHelper.startPage(pageNum, pageSize);
		 
	    List<FollowupRecord> dataList = followupRecordMapper.selectAll(searchDTO);
	
	    if(dataList.size() != 0){
	        PageInfo<FollowupRecord> pageInfo = new PageInfo<>(dataList);
	        for(FollowupRecord data:dataList){
	        	data.setFollowupTimeStr(DateUtils.dateToDateString(data.getFollowupTime()));
	        	data.setCreateTimeStr(DateUtils.dateToString(data.getCreateTime()));
            	data.setModifyTimeStr(DateUtils.dateToString(data.getModifyTime()));
            }
	        pageDataResult.setList(dataList);
	        pageDataResult.setTotals((int) pageInfo.getTotal());
	    }
	    return pageDataResult;
	}
	
	@Override
	public Map<String,Object> setData(MedicalOrgUser user,FollowupRecord rec){
		Map<String,Object> data = new HashMap<String,Object>();
		String operType = "",operContent="";
		Integer operDataId = 0;
        try {
        	int result = 0;
        	if(rec!=null && rec.getId() == null){
        		
        		rec.setCreatorId(user.getUserId());
        		rec.setCreator(user.getUserName());
        		rec.setCreateTime(new Date());
        		rec.setIsValid(Constants.Y);
                result = followupRecordMapper.insert(rec);
                
                operType = Constants.LOG_OPER_NEW;
                operDataId = rec.getId();
                operContent = DateUtils.dateToString(rec.getFollowupTime())+";"+rec.getFollowupType()+";"
                				+rec.getMemo();
        	}else if(rec!=null){
        		rec.setModifyTime(new Date());
        		result = followupRecordMapper.updateByPrimaryKey(rec);
        		
        		operType = Constants.LOG_OPER_EDIT;
        		operDataId = rec.getId();
        	}
            if(result == 0){
                data.put("code",Constants.CODE_FAILED);
                data.put("msg",Constants.FAILED);
                return data;
            }
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            
            logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_FOLLOW,operType,
            		operDataId+"", operContent, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
          		   new Date(), null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[新增和更新随访记录]异常！", e);
            return data;
        }
        return data;
	}
	
	@Override
	public Map<String, Object> deleteData(Integer id,MedicalOrgUser user){
		 Map<String, Object> data = new HashMap<String, Object>();
         try {
             int result = followupRecordMapper.deleteByPrimaryKey(id);
             if(result == 0){
                 data.put("code",Constants.CODE_FAILED);
                 data.put("msg",Constants.FAILED);
                 return data;
             }
             data.put("code",Constants.CODE_SUCCESS);
             data.put("msg",Constants.SUCCESS);
             
             logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_FOLLOW, Constants.LOG_OPER_DELE,
            		 id+"", null, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
           		   new Date(), null));
         } catch (Exception e) {
             e.printStackTrace();
             log.error("删除异常！", e);
         }
         return data;
	}
}
