package com.pdp.manager.service;

import java.util.List;

import com.pdp.manager.pojo.SysDictDetail;

/**
 * 系统字典管理业务接口
 * @author LIXr
 * @date 20/12/03
 */
public interface ISysDictService {

	public List<SysDictDetail> getAllDictDetailList();
}
