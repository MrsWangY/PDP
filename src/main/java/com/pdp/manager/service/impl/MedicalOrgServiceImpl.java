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
import com.pdp.manager.dao.LogSysMapper;
import com.pdp.manager.dao.MedicalOrgMapper;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.LogSys;
import com.pdp.manager.pojo.MedicalOrg;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IMedicalOrgService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class MedicalOrgServiceImpl implements IMedicalOrgService{

	@Autowired
	private MedicalOrgMapper medicalOrgMapper;
	@Autowired
	private LogSysMapper logSysMapper;
	
	@Override
	public List<MedicalOrg> getAllList(){
		return medicalOrgMapper.selectAll();
	}
	
	@Override
	public PageDataResult getList(SearchDTO searchDTO, Integer pageNum, Integer pageSize){
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<MedicalOrg> dataList = medicalOrgMapper.getList(searchDTO);
        if(dataList.size() != 0){
            PageInfo<MedicalOrg> pageInfo = new PageInfo<>(dataList);
            pageDataResult.setList(dataList);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;
    }
	
	@Override
	public Map<String,Object> setData(MedicalOrg org,MedicalOrgUser user){
		Map<String,Object> data = new HashMap<String,Object>();
		String operType = "",operContent="";
		String operDataId = "";
        try {
        	int result = 0;
        	if(org!=null && Constants.OPER_NEW.equals(org.getOper())){
        		MedicalOrg old = medicalOrgMapper.selectByPrimaryKey(org.getOrgCode());
                if(old != null){
                    data.put("code",Constants.CODE_FAILED);
                    data.put("msg","该医疗机构已存在！");
                    return data;
                }
                org.setIsValid(Constants.Y);
                result = medicalOrgMapper.insert(org);
                
                operType = Constants.LOG_OPER_NEW;
                operDataId = org.getOrgCode();
                operContent = org.getOrgCode()+";"+org.getOrgName();
        	}else if(org!=null && Constants.OPER_EDIT.equals(org.getOper())){
        		result = medicalOrgMapper.updateData(org);
        		
        		operType = Constants.LOG_OPER_EDIT;
        		operDataId = org.getOrgCode();
        	}
            if(result == 0){
                data.put("code",Constants.CODE_FAILED);
                data.put("msg",Constants.FAILED);
                return data;
            }
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            
            logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALORG, operType,
            		operDataId, operContent, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
       		   new Date(), null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[新增和更新医疗机构]异常！", e);
            return data;
        }
        return data;
	}
    
    @Override
    public Map<String, Object> deleteData(String orgCode,MedicalOrgUser user){
    	 Map<String, Object> data = new HashMap<String, Object>();
         try {
             int result = medicalOrgMapper.updateStatus(orgCode,Constants.N);
             if(result == 0){
                 data.put("code",Constants.CODE_FAILED);
                 data.put("msg",Constants.FAILED);
                 return data;
             }
             data.put("code",Constants.CODE_SUCCESS);
             data.put("msg",Constants.SUCCESS);
             
             logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALORG, Constants.LOG_OPER_DELE,
            		 orgCode, null, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
        		   new Date(), null));
         } catch (Exception e) {
             e.printStackTrace();
             log.error("删除异常！", e);
         }
         return data;
    }
}
