package com.pdp.manager.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
/**
 * 处方药品表
 * @author LIXr
 *
 */
@Getter
@Setter
@Table(name = "t_recipel_drug")
public class RecipelDrug {

	 /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique = true, nullable = false)
    private Integer id;
	
	/**
	 * 处方主键
	 */
	@JsonProperty(value="dataId")
	@Column(name = "data_id")
    private Integer dataId;
	
	 /**
     * 处方号（机构代码+处方号）
     */
	@JsonProperty(value="recipeNo")
	@Column(name = "recipe_no")
	private String recipeNo;	
	
	/**
	 * 药品中文名
	 */
	@JsonProperty(value="drugChnName")
	@Column(name = "drug_chn_name")
    private String drugChnName;

	/**
	 * 药品英文名
	 */
	@JsonProperty(value="drugEngName")
	@Column(name = "drug_eng_name")
    private String drugEngName;

	/**
	 * 医保码
	 */
	@JsonProperty(value="ybCode")
	@Column(name = "yb_code")
    private String ybCode;
	
	/**
	 * CAS号
	 */
	@JsonProperty(value="drugCode")
	@Column(name = "drug_code")
    private String drugCode;

	/**
	 * 备注
	 */
	@JsonProperty(value="memo")
	@Column(name = "memo")
    private String memo;

	/**
	 * 分类:麻醉MZ/第一类精神JYL/第二类精神JEL
	 */
	@JsonProperty(value="drugType")
	@Column(name = "drug_type")
    private String drugType;

	/**
	 * 用途分类：镇痛,镇静,催眠,抗焦虑
	 */
	@JsonProperty(value="drugUse")
	@Column(name = "drug_use")
    private String drugUse;

	/**
	 * 剂型:注射剂,控缓释剂型,其他剂型
	 */
	@JsonProperty(value="drugClass")
	@Column(name = "drug_class")
    private String drugClass;

	/**
	 * 患者分类：普通患者/麻卡患者
	 */
	@JsonProperty(value="patientClass")
	@Column(name = "patient_class")
    private String patientClass;

	/**
	 * 药品规格
	 */
	@JsonProperty(value="drugSpec")
	@Column(name = "drug_spec")
    private String drugSpec;

	/**
	 * 规格单位
	 */
	@JsonProperty(value="drugSpecUnit")
	@Column(name = "drug_spec_unit")
    private String drugSpecUnit;

	/**
	 * 批准文号
	 */
	@JsonProperty(value="drugApprnum")
	@Column(name = "drug_apprnum")
    private String drugApprnum;

	/**
	 * 每日/次最大用量
	 */
	@JsonProperty(value="dayUsed")
	@Column(name = "day_used")
    private String dayUsed;

	/**
	 * 每日用法（次）
	 */
	@JsonProperty(value="dayTimes")
	@Column(name = "day_times")
    private String dayTimes;

	/**
	 * 几日用量
	 */
	@JsonProperty(value="maxDays")
	@Column(name = "max_days")
    private Integer maxDays;
	
	//非数据库字段
	@Transient
	private String patientIdcard;	
	private Date recipelBuildDate;	
	private Integer recipelValidDay;	
	private Date recipelDispensingDate;	
	private Integer isDone;	
	private Integer diffDays;
}
