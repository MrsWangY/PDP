package com.pdp.manager.controller.system;

import java.util.HashMap;
import java.util.List;
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
import com.pdp.manager.service.IMedicalOrgUserService;

import lombok.extern.slf4j.Slf4j;
/**
 * 医疗结构人员管理业务控制层
 * @author LIXr
 *
 */
@Slf4j
@Controller
@RequestMapping("medicalOrgUser")
public class MedicalOrgUserController {

	@Autowired
    private IMedicalOrgUserService medicalOrgUserService;
	@Autowired
    private IMedicalOrgService medicalOrgService;
	
	/**
	 * 查询人员列表
	 * @param pageNum
	 * @param pageSize
	 * @param userSearch 含查询条件的用户信息
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ SearchDTO searchDTO) {
        /*logger.info("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",pageNum:" + page.getPageNum()
                + ",每页记录数量pageSize:" + page.getPageSize());*/
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
            pdr = medicalOrgUserService.getUserList(searchDTO, pageNum ,pageSize);
            pdr.setPageNum(pageNum);
            pdr.setPageSize(pageSize);
            List<MedicalOrg> orgList = medicalOrgService.getAllList();
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            data.put("data",pdr);
            data.put("orgList", orgList);
//            log.info("用户列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.EXCEPTION);
            log.error("用户列表查询异常！", e);
        }
        return data;
    }
	
	 /**
    *
    * 功能描述: 新增和更新用户
    *
    * @param:user 用户信息
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/setData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String,Object> setData(MedicalOrgUser user) {
       log.info("设置用户[新增或更新]！user:" + user);
       Map<String,Object> data = new HashMap<String,Object>();
       //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser loginU = (MedicalOrgUser) subject.getPrincipal();
       if(user.getUserId() == null){
           data = medicalOrgUserService.addUser(user,loginU);
       }else{
           data = medicalOrgUserService.updateUser(user,loginU);
       }
       return data;
   }


   /**
    *
    * 功能描述: 停用/启用 用户
    *
    * @param:userId 用户id
    * @param status 状态标记
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> updateStatus(@RequestParam("userId") Integer userId,@RequestParam("status") Integer status) {
	   log.info("停用/启用用户！userId:" + userId+" status:"+status);
       Map<String, Object> data = new HashMap<String,Object>();
       //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = medicalOrgUserService.updateUserStatus(userId,status,user);
       return data;
   }
   
   /**
   *
   * 功能描述: 重置密码
   *
   * @param: userId 用户id
   * @return:
   * @author: LIXr
   */
  @RequestMapping(value = "/resetPW", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> resetPW(@RequestParam("userId") Integer userId) {
	  log.info("重置密码！userId:" + userId);
      Map<String, Object> data = new HashMap<String,Object>();
      //登录用户
      Subject subject = SecurityUtils.getSubject();
      MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
      data = medicalOrgUserService.resetpw(userId,user);
      return data;
  }
  
  /**
   * 修改密码
   * @param userId
   * @return
   */
  @RequestMapping(value = "/modifyPW", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> modifyPW(@RequestParam("userId") Integer userId,@RequestParam("oldPassword") String oldPassword,
		  @RequestParam("newPassword") String newPassword ) {
	  log.info("修改密码！userId:" + userId);
      Map<String, Object> data = new HashMap<String,Object>();
      //登录用户
      Subject subject = SecurityUtils.getSubject();
      MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
      data = medicalOrgUserService.modifyPW(userId,oldPassword,newPassword,user);
      
      
      return data;
  }
}
