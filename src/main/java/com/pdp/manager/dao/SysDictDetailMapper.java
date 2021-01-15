package com.pdp.manager.dao;

import com.pdp.manager.pojo.SysDictDetail;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDictDetailMapper {
	
    public int deleteByPrimaryKey(@Param("id") Integer id);

    public int insert(SysDictDetail record);

    public SysDictDetail selectByPrimaryKey(@Param("id") Integer id);

    public List<SysDictDetail> selectAll();

    public int updateByPrimaryKey(SysDictDetail record);
}