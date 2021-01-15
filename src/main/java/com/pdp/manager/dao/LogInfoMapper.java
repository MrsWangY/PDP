package com.pdp.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.pojo.LogInfo;

@Repository
public interface LogInfoMapper{
	
	public int insert(LogInfo loginfo);
	
	public List<LogInfo> getList(SearchDTO searchDTO);
	
	public int alertData(@Param("recipeNo") String recipeNo);
	
	public int unAlertData(@Param("recipeNo") String recipeNo);
}
