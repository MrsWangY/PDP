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
import com.pdp.manager.common.utils.SysDictUtil;
import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.MedicalCase;
import com.pdp.manager.pojo.MedicalOrg;
import com.pdp.manager.pojo.MedicalOrgUser;
import com.pdp.manager.pojo.SysDictDetail;
import com.pdp.manager.redis.RedisUtil;
import com.pdp.manager.response.PageDataResult;
import com.pdp.manager.service.IMedicalCaseService;
import com.pdp.manager.service.IMedicalOrgService;
import com.pdp.manager.service.ISysDictService;

import lombok.extern.slf4j.Slf4j;
/**
 * 病例（麻卡）管理业务控制层
 * @author LIXr
 *
 */
@Slf4j
@Controller
@RequestMapping("medicalCase")
public class MedicalCaseController {

	@Autowired
    private IMedicalCaseService medicalCaseService;
	@Autowired
    private IMedicalOrgService medicalOrgService;
	@Autowired
	private ISysDictService sysDictService;
	
	@Autowired
    private RedisUtil redisService;
	/**
	 * 查询麻卡列表
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
            pdr = medicalCaseService.getList(searchDTO, pageNum ,pageSize);
            pdr.setPageNum(pageNum);
            pdr.setPageSize(pageSize);
            data.put("code",Constants.CODE_SUCCESS);
            data.put("msg",Constants.SUCCESS);
            data.put("data",pdr);
            
            List<MedicalOrg> orgList = medicalOrgService.getAllList();
            data.put("orgList", orgList);
            
            List<SysDictDetail> dictDetailList = sysDictService.getAllDictDetailList();
            Map<String,List<SysDictDetail>> mp = SysDictUtil.getDictionary(Constants.DICT_COMMON,dictDetailList);
            data.put("dictmp", mp);
        } catch (Exception e) {
            e.printStackTrace();
            data.put("code",Constants.CODE_FAILED);
            data.put("msg",Constants.EXCEPTION);
            log.error("列表查询异常！", e);
        }
        return data;
    }
	
	
	 /**
    *
    * 功能描述: 登记或更新麻卡
    *
    * @param: mc 麻卡信息
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/setData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String,Object> setData(MedicalCase mc) {
       log.info("登记或更新麻卡信息" );
       Map<String,Object> data = new HashMap<String,Object>();
       //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = medicalCaseService.setData(mc,redisService,user);
       return data;
   }

   /**
    *
    * 功能描述: 转诊
    *
    * @param:dataId 麻卡id
    * @param referralReason 转诊原因
    * @param hospCode 供药机构代码
    * @param hospName 供药机构名称
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/referral", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> referral(@RequestParam("dataId") Integer dataId,@RequestParam("referralReason") String referralReason,
		   @RequestParam("hospCode") String hospCode,@RequestParam("hospName") String hospName) {
	   log.info("转诊！dataId:" + dataId);
       Map<String, Object> data = new HashMap<String,Object>();
     //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = medicalCaseService.referral(dataId,referralReason,hospCode,hospName,redisService,user);
       return data;
   }

   /**
    *
    * 功能描述: 注销麻卡
    *
    * @param:dataId 麻卡id
    * @return:
    * @author: LIXr
    */
   @RequestMapping(value = "/cancellationData", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> cancellationData(@RequestParam("dataId") Integer dataId,
		   @RequestParam("cancellationReason") String cancellationReason) {
	   log.info("注销麻卡！dataId:" + dataId);
       Map<String, Object> data = new HashMap<String,Object>();
     //登录用户
       Subject subject = SecurityUtils.getSubject();
       MedicalOrgUser user = (MedicalOrgUser) subject.getPrincipal();
       data = medicalCaseService.cancellationData(dataId,cancellationReason,redisService,user);
       return data;
   }
   
   /**
   *
   * 功能描述: 根据机构代码查询麻卡患者数
   *
   * @param:dataId 麻卡id
   * @return:
   * @author: LIXr
   */
	@RequestMapping(value = "/getCaseListByOrgCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCaseListByOrgCode(@RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,@RequestParam("orgCode") String orgCode) {
		log.info("根据机构代码查询麻卡患者数！orgCode:" + orgCode);
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
			pdr = medicalCaseService.getCaseListByOrgCode(orgCode, pageNum ,pageSize);
			pdr.setPageNum(pageNum);
			pdr.setPageSize(pageSize);
			data.put("code",Constants.CODE_SUCCESS);
			data.put("msg",Constants.SUCCESS);
			data.put("data",pdr);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("code",Constants.CODE_FAILED);
			data.put("msg",Constants.EXCEPTION);
			log.error("列表查询异常！", e);
		}
		return data;
	}
  
}
