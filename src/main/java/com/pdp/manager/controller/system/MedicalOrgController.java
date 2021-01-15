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
import com.pdp.manager.pojo.MedicalOrg;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IMedicalOrgService;

import lombok.extern.slf4j.Slf4j;
/**
 * 医疗结构管理业务控制层
 * @author 
 *
 */
@Slf4j
@Controller
@RequestMapping("medicalOrg")
public class MedicalOrgController {

	@Autowired
    private IMedicalOrgService medicalOrgService;
	
	/**
	 * 查询机构列表
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
            // 获取用户列表
            pdr = medicalOrgService.getList(searchDTO, pageNum ,pageSize);
            pdr.setPageNum(pageNum);
            pdr.setPageSize(pageSize);
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            data.put("data",pdr);
//            log.info("医疗机构列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.EXCEPTION);
            log.error("医疗机构列表查询异常！", e);
        }
        return data;
    }
	
	 /**
    *
    * 功能描述: 新增和更新医疗机构
    *
    * @param: org 医疗机构信息
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/setData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String,Object> setData(MedicalOrg org) {
       log.info("设置医疗机构[新增或更新]！org:" + org);
       Map<String,Object> data = new HashMap<String,Object>();
       //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = medicalOrgService.setData(org,user);
       return data;
   }


   /**
    *
    * 功能描述: 删除医疗机构
    *
    * @param:orgCode 机构编码
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/deleteData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> deleteData(@RequestParam("orgCode") String orgCode) {
	   log.info("删除医疗机构！orgCode:" + orgCode);
       Map<String, Object> data = new HashMap<String,Object>();
       //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = medicalOrgService.deleteData(orgCode,user);
       return data;
   }
}
