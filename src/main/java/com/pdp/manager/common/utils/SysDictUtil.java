package com.pdp.manager.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pdp.manager.pojo.SysDictDetail;

public class SysDictUtil {

	/**
	 * 把所有配置信息按字典编码分类集合
	 * @param dictionarys 字典集合字符串
	 * @param dictDetailList 所有字典配置数据
	 * @return Map集合
	 */
	public static Map<String,List<SysDictDetail>> getDictionary(String dictionarys,List<SysDictDetail> dictDetailList) {
		Map<String,List<SysDictDetail>> dictmp = new HashMap<String,List<SysDictDetail>>();
		List<SysDictDetail> entityList = new ArrayList<SysDictDetail>();
		
		// 遍历所需字典类型
        for (String s : dictionarys.split(",")) {
        	List<SysDictDetail> temp = entityList;
        	dictDetailList.stream().forEach(
        			item->{ if(s.equals(((SysDictDetail)item).getDictCode())){
        				temp.add(item);
        		        }});
            dictmp.put(s, temp);
            entityList = null;
            entityList = new ArrayList<SysDictDetail>();
        }
        return dictmp;
    }
}
