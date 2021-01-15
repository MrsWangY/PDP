package com.pdp.manager.common.utils;

public class Constants {

	//常用变量
	public final static String FAILED = "失败";
	public final static String SUCCESS = "成功";
	public final static String EXCEPTION = "程序异常";
	public final static String FAILED_CODE = "02";
	public final static String SUCCESS_CODE = "01";
	public final static Integer N = 0; // 未.. 否
	public final static Integer Y = 1; // 已.. 是
	//与医院的业务接口交互日志相关变量
	public final static String LOG_OPER_TYPE_Y = "Y"; //验证处方
	public final static String LOG_OPER_TYPE_Q = "Q"; //Q强制开具处方有效化处方
	public final static String LOG_OPER_TYPE_M = "M"; //更新取药状态
	public final static String LOG_FAIL_REASON_CCYL = "CCYL";//超出规定用量
	public final static String LOG_FAIL_REASON_ORG_CF = "ORG_CF"; //重复开药
	public final static String LOG_FAIL_REASON_NO_DONE_CF = "NO_DONE_CF"; //重复开药
	public final static String LOG_FAIL_REASON_DRUG_CF = "DRUG_CF"; //重复开药
	public final static String LOG_FAIL_REASON_DRUG_USE_CF = "DRUG_USE_CF"; //重复开药
	public final static String LOG_FAIL_REASON_CS= "CS,参数问题"; //参数问题
	public final static String LOG_FAIL_REASON_NO_MK= "NO_MK,缺少麻卡记录"; //缺少麻卡记录
	public final static String LOG_FAIL_REASON_XT = "XT,系统问题"; //系统问题
	//麻卡系统操作日志变量
	//日志——模块
	public final static String LOG_OPER_MODULE_INDEX="首页"; 
	public final static String LOG_OPER_MODULE_MEDICALCASE="麻卡";
	public final static String LOG_OPER_MODULE_FOLLOW="随访";
	public final static String LOG_OPER_MODULE_MEDICALORG="医疗机构";
	public final static String LOG_OPER_MODULE_MEDICALORGUSER="医疗机构人员";
	public final static String LOG_OPER_MODULE_DRUGDICT="药品目录";
	public final static String LOG_OPER_MODULE_LOG="日志";
	//日志——操作
	public final static String LOG_OPER_LOGIN="登录";
	public final static String LOG_OPER_LOGOUT="登出";
	public final static String LOG_OPER_NEW="新增";
	public final static String LOG_OPER_EDIT="编辑";
	public final static String LOG_OPER_DELE="删除";
	public final static String LOG_OPER_DISABLE="停用";
	public final static String LOG_OPER_ENABLE="启用";
	public final static String LOG_OPER_RESETPW="重置密码";
	public final static String LOG_OPER_MODIFYPW="修改密码";
	public final static String LOG_OPER_CANCELLATION="注销";
	public final static String LOG_OPER_REFERRAL="转诊";
	public final static String LOG_OPER_ALERT="预警";
	public final static String LOG_OPER_UNALERT="取消预警";
	
	//系统角色
	public final static String ADMIN = "admin";
	public final static String TEST = "test";
	public final static String MANAGER = "manager";
	//操作结果编号
	public final static Integer CODE_FAILED = 0;
	public final static Integer CODE_SUCCESS = 1;
	public final static Integer CODE_TO_LOGIN = -1;
	//默认密码
	public final static String DEFAULT_PW= "MK@1234";
	//表单操作
	public final static String OPER_NEW = "new";
	public final static String OPER_EDIT = "edit";
	//数据字典
	public final static String DICT_XB="XB";
	public final static String DICT_MZ="MZ";
	public final static String DICT_HYZK="HYZK";
	public final static String DICT_ZYDM="ZYDM";
	//常用字典集合
	public final static String DICT_COMMON="XB,MZ,HYZK,ZYDM";
	//药品目录
	public final static String DRUG_TYPE_MZ="MZ";//麻醉药品
	public final static String DRUG_TYPE_JYL="JYL";//第一类精神药品
	public final static String DRUG_TYPE_JEL="JEL";//第二类精神药品
	//剂型
	public final static String DRUG_CLASS_ZSJ="注射剂";//:注射剂
	public final static String DRUG_CLASS_HSJ="控缓释剂型";//剂型:控缓释剂型
	public final static String DRUG_CLASS_QT="其他剂型";//剂型:其他剂型
	//做多几日用量
	public final static Integer MAX_DAY_3=3;
	public final static Integer MAX_DAY_7=7;
	public final static Integer MAX_DAY_15=15;
}
