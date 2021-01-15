package com.pdp.manager.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
/**
 * 药品目录
 * @author LIXr
 *	麻醉、精一类药品针剂3天，普通（其他）剂7天，缓控释制剂15天
	精二类药品统一最多7天用量
 */
@Getter
@Setter
@Table(name = "t_psychotropic_drug_dict")
public class PsychotropicDrugDict{
	
	 /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_id",unique = true, nullable = false)
    private Integer dataId;
	
	/**
	 * 药品中文名
	 */
	@Column(name = "drug_chn_name")
    private String drugChnName;

	/**
	 * 药品英文名
	 */
	@Column(name = "drug_eng_name")
    private String drugEngName;

	/**
	 * ATC码
	 */
	@Column(name = "yb_code")
    private String ybCode;
	
	/**
	 * CAS号
	 */
	@Column(name = "drug_code")
    private String drugCode;

	/**
	 * 备注
	 */
	@Column(name = "memo")
    private String memo;

	/**
	 * 分类:麻醉MZ/第一类精神JYL/第二类精神JEL
	 */
	@Column(name = "drug_type")
    private String drugType;

	/**
	 * 用途分类：镇痛,镇静,催眠,抗焦虑
	 */
	@Column(name = "drug_use")
    private String drugUse;

	/**
	 * 剂型:注射剂,控缓释剂型,其他剂型
	 */
	@Column(name = "drug_class")
    private String drugClass;

	/**
	 * 患者分类：普通患者/麻卡患者
	 */
	@Column(name = "patient_class")
    private String patientClass;

	/**
	 * 药品规格
	 */
	@Column(name = "drug_spec")
    private String drugSpec;

	/**
	 * 规格单位
	 */
	@Column(name = "drug_spec_unit")
    private String drugSpecUnit;

	/**
	 * 批准文号
	 */
	@Column(name = "drug_apprnum")
    private String drugApprnum;

	/**
	 * 每日/次最大用量
	 */
	@Column(name = "day_used")
    private String dayUsed;

	/**
	 * 每日用法（次）
	 */
	@Column(name = "day_times")
    private String dayTimes;

	/**
	 * 最大疗程天数
	 */
	@Column(name = "max_days")
    private Integer maxDays;
	
	/**
	 * 是否补录0否,1是)
	 */
	@Column(name = "is_new")
	private Integer isNew;
	
	/**
	 * 新增人编号
	 */
	@Column(name = "creator_id")
	private Integer creatorId;
	
	/**
	 * 新增人
	 */
	@Column(name = "creator")
	private String creator;
	
	/**
	 * 新增时间
	 */
	@Column(name = "create_date")
	private Date createDate;
	
	/**
	 * 修改时间
	 */
	@Column(name = "modify_date")
	private Date modifyDate;
	
	/**
	 * 是否可用(0否,1是)
	 */
	@Column(name = "is_valid")
	private Integer isValid;
	
	//非数据库字段
	@Transient
	private String createDateStr;
	private String modifyDateStr;

}