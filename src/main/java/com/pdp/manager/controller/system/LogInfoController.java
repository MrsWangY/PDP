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
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.ILogInfoSerevice;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志业务控制层
 * @author LIXr
 *
 */
@Slf4j
@Controller
@RequestMapping("logInfo")
public class LogInfoController {

	@Autowired
	private ILogInfoSerevice logInfoSerevice;
	
	/**
	 * 查询日志记录
	 * @param pageNum
	 * @param pageSize
	 * @param searchDTO
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
            // 获取重复开药记录
            pdr = logInfoSerevice.getList(searchDTO, pageNum ,pageSize);
            pdr.setPageNum(pageNum);
            pdr.setPageSize(pageSize);
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            data.put("data",pdr);

        } catch (Exception e) {
            e.printStackTrace();
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.EXCEPTION);
            log.error("日志记录查询异常！", e);
        }
        return data;
    }
	
	/**
	 * 预警
	 * @param dataId
	 * @return
	 */
	@RequestMapping(value = "/alertData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> alertData(@RequestParam("recipeNo") String recipeNo) {
	   log.info("预警！recipeNo:" + recipeNo);
       Map<String, Object> data = new HashMap<String,Object>();
       
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = logInfoSerevice.alertData(recipeNo,user);
       return data;
	}
	
	/**
	 * 取消预警
	 * @param dataId
	 * @return
	 */
	@RequestMapping(value = "/unAlertData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAlertData(@RequestParam("recipeNo") String recipeNo) {
	   log.info("取消预警！recipeNo:" + recipeNo);
       Map<String, Object> data = new HashMap<String,Object>();
       
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = logInfoSerevice.unAlertData(recipeNo,user);
       return data;
	}
}
