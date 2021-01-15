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
import com.pdp.manager.pojo.PsychotropicDrugDict;
import com.pdp.manager.redis.RedisUtil;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IDrugDictService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("drugDict")
public class DrugDictController {

	@Autowired
	private IDrugDictService drugDictService;
	
	@Autowired
    private RedisUtil redisService;
	/**
	 * 查询列表
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
            // 获取列表
            pdr = drugDictService.getList(searchDTO, pageNum ,pageSize);
            pdr.setPageNum(pageNum);
            pdr.setPageSize(pageSize);
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            data.put("data",pdr);

        } catch (Exception e) {
            e.printStackTrace();
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.EXCEPTION);
            log.error("药品字典列表查询异常！", e);
        }
        return data;
    }
	
	 /**
    *
    * 功能描述: 新增和更新
    *
    * @param: pd 包含表单信息的对象
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/setData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String,Object> setData(PsychotropicDrugDict pd) {
       log.info("设置[新增或更新]！pd:" + pd);
       Map<String,Object> data = new HashMap<String,Object>();
       
       //用户查询列表权限
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = drugDictService.setData(user,pd,redisService);
       return data;
   }


   /**
    *
    * 功能描述: 删除
    *
    * @param:dataId 主键
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/deleteData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> deleteData(@RequestParam("dataId") Integer dataId) {
	   log.info("删除！dataId:" + dataId);
       Map<String, Object> data = new HashMap<String,Object>();
       //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = drugDictService.deleteData(dataId,redisService,user);
       return data;
   }
}
