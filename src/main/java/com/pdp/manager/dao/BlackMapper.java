package com.pdp.manager.dao;

import com.pdp.manager.pojo.Black;

import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public interface BlackMapper {
   
	public int deleteByPrimaryKey(Integer id);

	public int insert(Black record);

	public Black selectByPrimaryKey(Integer id);

	public List<Black> selectAll();

	public int updateByPrimaryKey(Black record);
}