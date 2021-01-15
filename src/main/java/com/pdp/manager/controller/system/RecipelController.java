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
import com.pdp.manager.service.IRecipelSerevice;

import lombok.extern.slf4j.Slf4j;
/**
 * 处方业务控制层
 * @author LIXr
 *
 */
@Slf4j
@Controller
@RequestMapping("recipel")
public class RecipelController {

	@Autowired
	private IRecipelSerevice recipelSerevice;
	
	/**
	 * 查询就诊记录
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getRecipelRecord", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getRecipelRecord(@RequestParam("pageNum") Integer pageNum,
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
            	
            pdr = recipelSerevice.getRecipelRecord(searchDTO, pageNum ,pageSize);
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
