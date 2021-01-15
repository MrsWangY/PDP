package com.pdp.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdp.manager.dao.SysDictDetailMapper;
import com.pdp.manager.dao.SysDictMapper;
import com.pdp.manager.pojo.SysDictDetail;
import com.pdp.manager.service.ISysDictService;
/**
 * 字典管理业务实现service
 * @author LIXr
 * @date 20/11/17
 */
@Service
public class SysDictSereviceImpl implements ISysDictService{
	
	@Autowired
    private SysDictMapper sysDictMapper;
	@Autowired
	private SysDictDetailMapper sysDictDetailMapper;
	
	@Override
	public List<SysDictDetail> getAllDictDetailList(){
		return sysDictDetailMapper.selectAll();
	}
	
}
