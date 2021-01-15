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
import com.pdp.manager.common.utils.DigestUtils;
import com.pdp.manager.dao.LogSysMapper;
import com.pdp.manager.dao.MedicalOrgUserMapper;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.LogSys;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IMedicalOrgUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicalOrgUserServiceImpl implements IMedicalOrgUserService{

	@Autowired
	private MedicalOrgUserMapper medicalOrgUserMapper;
	@Autowired
	private LogSysMapper logSysMapper;
	
	@Override
    public PageDataResult getUserList(SearchDTO searchDTO, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        
        List<MedicalOrgUser> dataList = medicalOrgUserMapper.getUserList(searchDTO);

        if(dataList.size() != 0){
            PageInfo<MedicalOrgUser> pageInfo = new PageInfo<>(dataList);
            for(MedicalOrgUser data:dataList){
	        	data.setCreateDateStr(DateUtils.dateToString(data.getCreateDate()));
            }
            pageDataResult.setList(dataList);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;
    }
	 
	@Override
	public MedicalOrgUser findByLoginName(String loginName){
		return medicalOrgUserMapper.findByLoginName(loginName);
	}
	
	@Override
	public MedicalOrgUser getUserByKeyCode(String CACode){
		return medicalOrgUserMapper.getUserByKeyCode(CACode);
	}
	
	@Override
	public Map<String,Object> addUser(MedicalOrgUser user,MedicalOrgUser loginUser){
		Map<String,Object> data = new HashMap<String,Object>();
        try {
        	MedicalOrgUser old = medicalOrgUserMapper.findByLoginName(user.getLoginName());
            if(old != null){
                data.put("code",Constants.CODE_FAILED);
                data.put("msg","登录账户已存在！");
                return data;
            }
            String username = user.getLoginName();
            String password = DigestUtils.Md5(username,Constants.DEFAULT_PW);
            user.setLoginPwd(password);
            user.setCreateDate(DateUtils.getCurrentDateToDate());
            user.setRole(Constants.MANAGER);
            user.setIsValid(Constants.Y);
            int result = medicalOrgUserMapper.insert(user);
            if(result == 0){
                data.put("code",Constants.CODE_FAILED);
                data.put("msg",Constants.FAILED);
                return data;
            }
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            
            String operContent = user.getUserName()+";"+user.getOrgName()+";"+user.getLoginName()+";"+user.getCACode();
            logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALORGUSER, Constants.LOG_OPER_NEW,
            		user.getUserId()+"", operContent, Constants.SUCCESS, "", loginUser.getUserId(), loginUser.getUserName(),
          		   new Date(), null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户[新增]异常！", e);
            return data;
        }
        return data;
	}

	@Override
	public Map<String,Object> updateUser(MedicalOrgUser user,MedicalOrgUser loginUser){
		Map<String,Object> data = new HashMap<String,Object>();
        int result = medicalOrgUserMapper.updateUser(user);
        if(result == 0){
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.FAILED);
            return data;
        }
        data.put("code",Constants.CODE_SUCCESS);
        data.put("msg",Constants.SUCCESS);
        
        String operContent = "编辑后："+user.getUserName()+";"+user.getOrgName()+";"+user.getLoginName()+";"+user.getCACode();
        logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALORGUSER, Constants.LOG_OPER_EDIT,
        		user.getUserId()+"", operContent, Constants.SUCCESS, "", loginUser.getUserId(), loginUser.getUserName(),
      		   new Date(), null));
        return data;
	}
    
    @Override
	public Map<String, Object> updateUserStatus(Integer userId,Integer status,MedicalOrgUser user){
    	 Map<String, Object> data = new HashMap<String, Object>();
         try {
             int result = medicalOrgUserMapper.updateUserStatus(userId,status);
             if(result == 0){
                 data.put("code",Constants.CODE_FAILED);
                 data.put("msg",Constants.FAILED);
                 return data;
             }
             data.put("code",Constants.CODE_SUCCESS);
             data.put("msg",Constants.SUCCESS);
             
             String operType = status==1?Constants.LOG_OPER_ENABLE:Constants.LOG_OPER_DISABLE;
             logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALORGUSER, operType,
             		userId+"", null, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
           		   new Date(), null));
         } catch (Exception e) {
             e.printStackTrace();
             log.error("停用/启用用户异常！", e);
         }
         return data;
    }
    
    @Override
    public Map<String, Object> resetpw(Integer userId,MedicalOrgUser user){
    	Map<String, Object> data = new HashMap<String, Object>();
    	MedicalOrgUser old = medicalOrgUserMapper.selectByPrimaryKey(userId);
        if(old == null){
            data.put("code",Constants.CODE_FAILED);
            data.put("msg","用户信息获取失败！");
            return data;
        }
        String username = old.getLoginName();
        String password = DigestUtils.Md5(username,Constants.DEFAULT_PW);
        int result = medicalOrgUserMapper.resetpw(userId,password);
        if(result == 0){
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.FAILED);
            return data;
        }
        data.put("code",Constants.CODE_SUCCESS);
        data.put("msg",Constants.SUCCESS);
        
        logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALORGUSER, Constants.LOG_OPER_RESETPW,
        		userId+"", null, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
      		   new Date(), null));
        return data;
    }

    public Map<String, Object> modifyPW(Integer userId,String oldPassword,String newPassword,MedicalOrgUser user){
    	Map<String, Object> data = new HashMap<String, Object>();
    	MedicalOrgUser old = medicalOrgUserMapper.selectByPrimaryKey(userId);
        if(old == null){
            data.put("code",Constants.CODE_FAILED);
            data.put("msg","用户信息获取失败！");
            return data;
        }
        String username = old.getLoginName();
        String password = DigestUtils.Md5(username,oldPassword);
        if(oldPassword!=null && !password.equals(old.getLoginPwd())){
        	data.put("code",Constants.CODE_FAILED);
            data.put("msg","原密码错误！");
            return data;
        }
        password = DigestUtils.Md5(username,newPassword);
        int result = medicalOrgUserMapper.resetpw(userId,password);
        if(result == 0){
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.FAILED);
            return data;
        }
        data.put("code",Constants.CODE_SUCCESS);
        data.put("msg",Constants.SUCCESS);
        
        logSysMapper.insert(new LogSys(Constants.LOG_OPER_MODULE_MEDICALORGUSER, Constants.LOG_OPER_MODIFYPW,
        		userId+"", null, Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
      		   new Date(), null));
        return data;
    }
}
