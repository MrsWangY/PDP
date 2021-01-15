package com.pdp.manager.dao;

import com.pdp.manager.pojo.TakeMediRecord;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TakeMediRecordMapper {
	public int deleteByPrimaryKey(Integer data_id);

	public int insert(TakeMediRecord record);

	public TakeMediRecord selectByPrimaryKey(Integer data_id);

	public List<TakeMediRecord> selectAll();

	public int updateByPrimaryKey(TakeMediRecord record);
}