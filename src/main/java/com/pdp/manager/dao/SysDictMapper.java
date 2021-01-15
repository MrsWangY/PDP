package com.pdp.manager.dao;

import com.pdp.manager.pojo.SysDict;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface SysDictMapper {
    public int deleteByPrimaryKey(@Param("id") Integer id);

    public int insert(SysDict record);

    public SysDict selectByPrimaryKey(@Param("id") Integer id);

    public List<SysDict> selectAll();

    public int updateByPrimaryKey(SysDict record);
}