package com.pdp.manager.dao;

import com.pdp.manager.pojo.TakeMediHandbook;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TakeMediHandbookMapper {
	public int deleteByPrimaryKey(Integer data_id);

	public int insert(TakeMediHandbook record);

	public TakeMediHandbook selectByPrimaryKey(Integer data_id);

	public List<TakeMediHandbook> selectAll();

	public int updateByPrimaryKey(TakeMediHandbook record);
}