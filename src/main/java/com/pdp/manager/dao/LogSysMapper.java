package com.pdp.manager.dao;

import com.pdp.manager.pojo.LogSys;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface LogSysMapper {
	
	public int insert(LogSys record);

	public List<LogSys> selectAll();
}