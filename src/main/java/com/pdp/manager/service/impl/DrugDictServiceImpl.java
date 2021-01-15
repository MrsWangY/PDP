package com.pdp.manager.service.impl;

import java.util.ArrayList;
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
import com.pdp.manager.dao.LogSysMapper;
import com.pdp.manager.dao.PsychotropicDrugDictMapper;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.LogSys;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.pojo.PsychotropicDrugDict;
import com.pdp.manager.redis.RedisUtil;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IDrugDictService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DrugDictServiceImpl implements IDrugDictService{
	
	@Autowired
	private PsychotropicDrugDictMapper drugDictMapper;
	@Autowired
	private LogSysMapper logSysMapper;

	@Override
	public PageDataResult getList(SearchDTO searchDTO,Integer pageNum,Integer pageSize){
		  PageDataResult pageDataResult = new PageDataResult();
	        PageHelper.startPage(pageNum, pageSize);
	        List<PsychotropicDrugDict> dataList = drugDictMapper.getList(searchDTO);
	        if(dataList.size() != 0){
	            PageInfo<PsychotropicDrugDict> pageInfo = new PageInfo<>(dataList);
	            for(PsychotropicDrugDict data:dataList){
	            	data.setCreateDateStr(DateUtils.dateToString(data.getCreateDate()));
	            	data.setModifyDateStr(DateUtils.dateToString(data.getModifyDate()));
	            }
	            pageDataResult.setList(dataList);
	            pageDataResult.setTotals((int) pageInfo.getTotal());
	        }

	        return pageDataResult;
	}
	
	@Override
	public Map<String,Object> setData(MedicalOrgUser user,PsychotropicDrugDict pd,RedisUtil redisService){
		Map<String,Object> data = new HashMap<String,Object>();
		Map map=redisService.getHash("redisDB");
		List<PsychotropicDrugDict> drugList = map==null?null:(List<PsychotropicDrugDict>)map.get("drugList");
		String operType = "",operContent="";
		Integer operDataId = 0;
        try {
        	int result = 0;
        	if(pd!=null && pd.getDataId() == null){
        		pd.setCreatorId(user.getUserId());
        		pd.setCreator(user.getUserName());
        		pd.setCreateDate(new Date());
        		pd.setIsValid(Constants.Y);
        		pd.setIsNew(Constants.Y); //是否为补录数据
                result = drugDictMapper.insert(pd);
                
                if(drugList==null)  drugList = new ArrayList<PsychotropicDrugDict>();
                drugList.add(pd);
                
                operType = Constants.LOG_OPER_NEW;
                operDataId = pd.getDataId();
                operContent = pd.getDrugChnName()+";"+pd.getDrugCode()+";"+pd.getDrugType()+";"+pd.getDrugUse()
                				+";"+pd.getDrugClass()+";"+pd.getMaxDays();
        	}else if(pd!=null){
        		pd.setModifyDate(new Date());
        		result = drugDictMapper.updateByPrimaryKey(pd);
        		
        		for(int i=0;i<drugList.size();i++){
         			if(drugList.get(i).getDataId()==pd.getDataId()){
         				drugList.set(i, pd);
         				break;
         			}
         		}
        		
        		operType = Constants.LOG_OPER_EDIT;
        		operDataId = pd.getDataId();
        	}
            if(result == 0){
                data.put("code",Constants.CODE_FAILED);
                data.put("msg",Constants.FAILED);
                return data;
            }
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            
            if(map!=null){
	           	log.info("update redis...");
	           	map.put("drugList",drugList); //更新药品字典
	   			 //redis中添加hash
	   	        redisService.setHash("redisDB",map);
           }else{
        	   log.info("redis empty...");
           }
            
            logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_DRUGDICT, operType,
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
	public Map<String,Object> deleteData(Integer dataId,RedisUtil redisService,MedicalOrgUser user){
		 Map<String, Object> data = new HashMap<String, Object>();
		 Map map=redisService.getHash("redisDB");
		 List<PsychotropicDrugDict> drugList = map==null?null:(List<PsychotropicDrugDict>)map.get("drugList");
         try {
             int result = drugDictMapper.deleteByPrimaryKey(dataId);
             if(result == 0){
                 data.put("code",Constants.CODE_FAILED);
                 data.put("msg",Constants.FAILED);
                 return data;
             }
             data.put("code",Constants.CODE_SUCCESS);
             data.put("msg",Constants.SUCCESS);
             
             if(map!=null){
            	 for(int i=0;i<drugList.size();i++){
          			if(drugList.get(i).getDataId()==dataId){
          				drugList.remove(i);
          				break;
          			}
          		}
            	log.info("update redis...");
            	map.put("drugList",drugList); //更新药品字典
    			 //redis中添加hash
    	        redisService.setHash("redisDB",map);
            }else{
            	log.info("redis empty...");
            }
             
             logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_DRUGDICT, Constants.LOG_OPER_DELE,
            		 dataId+"", null, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
        		   new Date(), null));
         } catch (Exception e) {
             e.printStackTrace();
             log.error("删除异常！", e);
         }
         return data;
	}
}
