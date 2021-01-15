package com.pdp.manager.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pdp.manager.common.utils.Constants;
import com.pdp.manager.dto.LoginDTO;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.service.ILogSysSerevice;
import com.pdp.manager.service.IMedicalOrgUserService;

import lombok.extern.slf4j.Slf4j;
/**
 * 登录业务
 * @author LIXr
 * @date 2020-11-24
 */
@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
    private IMedicalOrgUserService medicalOrgUserService;
	@Autowired
	private ILogSysSerevice logSysSerevice;
	/**
    *
    * 功能描述: 登入系统
    *
    * @param:
    * @return:
    * @author: LIXr
    */
	@RequestMapping("login")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request, LoginDTO loginDTO, HttpSession session){
       log.info("进行登陆验证");
       Map<String,Object> data = new HashMap<String,Object>();
       // 使用 shiro 进行登录
       Subject subject = SecurityUtils.getSubject();

       if(loginDTO==null || loginDTO.getUsername()==null || loginDTO.getPassword()==null){
    	   data.put("code",Constants.CODE_TO_LOGIN);
           data.put("message","账号信息为空");
           log.error("账号信息为空");
           return data;
       }
       String userName = loginDTO.getUsername().trim();
       String password = loginDTO.getPassword().trim();
       String rememberMe = loginDTO.getRememberMe();
       String host = request.getRemoteAddr();

       //获取token
       UsernamePasswordToken token = new UsernamePasswordToken(userName, password,host);

       // 设置 remenmberMe 的功能
       if (rememberMe != null && rememberMe.equals("on")) {
           token.setRememberMe(true);
       }

       try {
           subject.login(token);
           // 登录成功
           MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
           user.setLoginPwd(null);
           session.setAttribute("user", user.getUserName());
           data.put("code",Constants.CODE_SUCCESS);
           data.put("url","/home");
           data.put("message","登陆成功");
           data.put("data",user);
           log.info(user.getUserName()+"登陆成功");
           logSysSerevice.insertLog(Constants.LOG_OPER_MODULE_INDEX, Constants.LOG_OPER_LOGIN,
        		   null, "【"+user.getUserName()+"】登录系统", Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
        		   new Date(), null);
       } catch (UnknownAccountException e) {
           data.put("code",Constants.CODE_FAILED);
           data.put("message",userName+"账号不存在");
           log.error(userName+"账号不存在");
           return data;
       }catch (DisabledAccountException e){
           data.put("code",Constants.CODE_FAILED);
           data.put("message",userName+"账号异常");
           log.error(userName+"账号异常");
           return data;
       }catch (AuthenticationException e){
//    	   e.printStackTrace();
           data.put("code",Constants.CODE_FAILED);
           data.put("message",userName+"密码错误");
           log.error(userName+"密码错误");
           return data;
       }

       return data;
   }

   /**
   *
   * 功能描述: 登入系统
   *
   * @param:
   * @return:
   * @author: LIXr
   */
  @RequestMapping("loginCA")
  @ResponseBody
  public Map<String,Object> loginCA(HttpServletRequest request, LoginDTO loginDTO, HttpSession session){
      log.info("进行登陆验证");
      
      Map<String,Object> data = new HashMap<String,Object>();
      // 使用 shiro 进行登录
      Subject subject = SecurityUtils.getSubject();
      
      if(!(loginDTO!=null && "tologin".equals(loginDTO.getOper()))){
    	  data.put("code",Constants.CODE_TO_LOGIN);
    	  data.put("message","账号信息为空");
    	  log.error("账号信息为空");
    	  return data;
      }
      
      //根据卡号直接取到用户信息给shiro认证
      MedicalOrgUser user = medicalOrgUserService.getUserByKeyCode(loginDTO.getCode());
      if(user==null){
    	  data.put("code",Constants.CODE_FAILED);
    	  data.put("message","该key未与麻卡系统账户绑定");
    	  log.error("该key未与麻卡系统账户绑定");
    	  return data;
      }
      
      String userName = loginDTO.getUsername().trim();
      String password = loginDTO.getPassword().trim();
      String host = request.getRemoteAddr();

      if(!user.getLoginName().equals(userName)){
    	  data.put("code",Constants.CODE_FAILED);
    	  data.put("message","登录账户与key绑定账户不一致");
    	  log.error("登录账户与key绑定账户不一致");
    	  return data;
      }
      //获取token
      UsernamePasswordToken token = new UsernamePasswordToken(userName, password,host);

//      // 设置 remenmberMe 的功能
//      if (rememberMe != null && rememberMe.equals("on")) {
//          token.setRememberMe(true);
//      }

      try {
          subject.login(token);
          // 登录成功
          user = (MedicalOrgUser) subject.getPrincipal();
          
          user.setLoginPwd(null);
          session.setAttribute("user", user.getUserName());
          data.put("code",Constants.CODE_SUCCESS);
          data.put("url","/home");
          data.put("message","登陆成功");
          data.put("data",user);
          log.info(user.getUserName()+"登陆成功");
          logSysSerevice.insertLog(Constants.LOG_OPER_MODULE_INDEX, Constants.LOG_OPER_LOGIN,
       		   null, "【"+user.getUserName()+"】登录系统", Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
       		   new Date(), null);
      } catch (UnknownAccountException e) {
          data.put("code",Constants.CODE_FAILED);
          data.put("message",userName+"账号不存在");
          log.error(userName+"账号不存在");
          return data;
      }catch (DisabledAccountException e){
          data.put("code",Constants.CODE_FAILED);
          data.put("message",userName+"账号异常");
          log.error(userName+"账号异常");
          return data;
      }catch (AuthenticationException e){
//   	   e.printStackTrace();
          data.put("code",Constants.CODE_FAILED);
          data.put("message",userName+"密码错误");
          log.error(userName+"密码错误");
          return data;
      }

      return data;
	}
  	
	@RequestMapping("logout")
	@ResponseBody
	public Map<String,Object> logout(){
       log.info("退出系统");
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       subject.logout(); // shiro底层删除session的会话信息
       
       Map<String,Object> data = new HashMap<String,Object>();
       data.put("code",Constants.CODE_SUCCESS);
       data.put("message","退出系统");
       
       logSysSerevice.insertLog(Constants.LOG_OPER_MODULE_INDEX, Constants.LOG_OPER_LOGOUT,
       		   null, "【"+user.getUserName()+"】退出系统", Constants.SUCCESS, "", user.getUserId(), user.getUserName(),
       		   new Date(), null);
       return data;
	}

}
