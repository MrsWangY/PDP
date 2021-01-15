package com.pdp.manager.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdp.manager.pojo.MedicalCase;
import com.pdp.manager.pojo.PsychotropicDrugDict;
import com.pdp.manager.service.IMedicalCaseService;
import com.pdp.manager.service.IRecipelDrugSerevice;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author LIXr
 *
 */
@Slf4j
@Service
public class InitStartService {

	@Autowired
    private IMedicalCaseService medicalCaseService;
	@Autowired
	private IRecipelDrugSerevice recipelDrugSerevice;
	@Autowired
    private RedisUtil redisService;
	
	public void initData() {
		
		Map map=new HashMap<>();
		List<MedicalCase> mcList = medicalCaseService.init();
		List<PsychotropicDrugDict> drugList = recipelDrugSerevice.init();
		
		if (CollectionUtils.isEmpty(mcList) || CollectionUtils.isEmpty(drugList)) {
	    	log.error("project start init redis mcList data error !!");
	    	log.info("项目启动加载数据到redis失败，请尽快处理！");
		}else{
			log.info("init redis data ,mcList size ：{} ", mcList.size());
			log.info("init redis data ,drugList size ：{} ", drugList.size());
			map.put("mcList",mcList);
			map.put("drugList",drugList);
			 //redis中添加hash
	        redisService.setHash("redisDB",map);
		}
	}
}
