package com.pdp.manager.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pdp.manager.common.utils.Constants;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.FollowupRecord;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IFollowupService;

import lombok.extern.slf4j.Slf4j;
/**
 * 随访业务控制层
 * @author LIXr
 * @date 2020-12-14
 */
@Slf4j
@Controller
@RequestMapping("followup")
public class FollowupController {

	@Autowired
	private IFollowupService followupService;
	
	/**
	 * 提示随访列表
	 * @param pageNum
	 * @param pageSize
	 * @param searchDTO
	 * @return
	 */
	@RequestMapping(value = "/getWarnList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getWarnList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      SearchDTO searchDTO) {
		 Map<String,Object> data = new HashMap<String,Object>();
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
          //用户查询列表权限
            Subject subject = SecurityUtils.getSubject();
            MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
            if(user!=null && !Constants.ADMIN.equals(user.getRole())){
            	if(searchDTO==null) 
            		searchDTO = new SearchDTO();
            	searchDTO.setOrgCode(user.getOrgCode());
            }
            
            // 获取用户列表
            pdr = followupService.getWarnList(searchDTO, pageNum ,pageSize);
            pdr.setPageNum(pageNum);
            pdr.setPageSize(pageSize);
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            data.put("data",pdr);

        } catch (Exception e) {
            e.printStackTrace();
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.EXCEPTION);
            log.error("提示随访记录查询异常！", e);
        }
        return data;
    }
	
	/**
	 * 查询随访记录列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      SearchDTO searchDTO) {
		 Map<String,Object> data = new HashMap<String,Object>();
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
          //用户查询列表权限
            Subject subject = SecurityUtils.getSubject();
            MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
            if(user!=null && !Constants.ADMIN.equals(user.getRole())){
            	if(searchDTO==null) 
            		searchDTO = new SearchDTO();
            	searchDTO.setOrgCode(user.getOrgCode());
            }
            
            // 获取用户列表
            pdr = followupService.getList(searchDTO, pageNum ,pageSize);
            pdr.setPageNum(pageNum);
            pdr.setPageSize(pageSize);
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            data.put("data",pdr);

        } catch (Exception e) {
            e.printStackTrace();
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.EXCEPTION);
            log.error("随访记录查询异常！", e);
        }
        return data;
    }
	
	 /**
    *
    * 功能描述: 新增和更新随访记录
    *
    * @param: rec 随访记录
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/setData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String,Object> setData(FollowupRecord rec) {
       log.info("设置随访记录[新增或更新]！rec:" + rec);
       Map<String,Object> data = new HashMap<String,Object>();
       //用户查询列表权限
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = followupService.setData(user,rec);
       return data;
   }


   /**
    *
    * 功能描述: 删除随访记录
    *
    * @Param:id 随访记录主键
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/deleteData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> deleteData(@RequestParam("id") Integer id) {
	   log.info("删除随访记录！id:" + id);
       Map<String, Object> data = new HashMap<String,Object>();
       //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = followupService.deleteData(id,user);
       return data;
   }
}
