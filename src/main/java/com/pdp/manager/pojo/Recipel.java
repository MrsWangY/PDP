package com.pdp.manager.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
/**
 * 处方记录表 * @author LIXr
 *
 */
@Getter
@Setter
@Table(name = "t_recipel")
public class Recipel {

	 /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_id",unique = true, nullable = false)
    private Integer dataId;
	
	 /**
     * 医疗机构代码
     */
	@JsonProperty(value="orgCode")
	@Column(name = "org_code")
	private String orgCode;	
	
	/**
     * 医疗机构名称
     */
	@JsonProperty(value="orgName")
	@Column(name = "org_name")
	private String orgName ;	
	
	/**
     * 处方号（机构代码+处方号）
     */
	@JsonProperty(value="recipeNo")
	@Column(name = "recipe_no")
	private String recipeNo;	
	
	/**
     * 处方分类 门诊/住院
     */
	@JsonProperty(value="recipeType")
	@Column(name = "recipe_type")
	private String recipeType;	
	
	/**
     * 院内处方开立科室编号
     */
	@JsonProperty(value="departNoExp")
	@Column(name = "depart_no_exp")
	private String departNoExp;	
	
	/**
     * 院内处方开立科室名称
     */
	@JsonProperty(value="departNameExp")
	@Column(name = "depart_name_exp")
	private String departNameExp;	
	
	/**
     * 处方开立医师编号
     */
	@JsonProperty(value="recipelBuildDoctNo")
	@Column(name = "recipel_build_doct_no")
	private String recipelBuildDoctNo;	
	
	/**
     * 处方开立医师签名
     */
	@JsonProperty(value="recipelBuildDoctName")
	@Column(name = "recipel_build_doct_name")
	private String recipelBuildDoctName;	
	
	/**
     * 患者姓名
     */
	@JsonProperty(value="patientName")
	@Column(name = "patient_name")
	private String patientName;	
	
	/**
     * 患者身份证号
     */
	@JsonProperty(value="patientIdcard")
	@Column(name = "patient_idcard")
	private String patientIdcard;	
	
	/**
     * 是否有身份证 考虑非国人就诊开药(0无,1有)
     */
	@JsonProperty(value="isIdcardExist")
	@Column(name = "is_idcard_exist")
	private Integer isIdcardExist;	
	
	/**
     * 证件类别代码
     */
	@JsonProperty(value="idCardTypeCode")
	@Column(name = "id_card_type_code")
	private String idCardTypeCode;	
	
	/**
     * 证件类别名称
     */
	@JsonProperty(value="idCardTypeName")
	@Column(name = "id_card_type_name")
	private String idCardTypeName;	
	
	/**
     * 证件号码
     */
	@JsonProperty(value="idCardValue")
	@Column(name = "id_card_value")
	private String idCardValue;	
	
	/**
     * 患者性别代码
     */
	@JsonProperty(value="genderCode")
	@Column(name = "gender_code")
	private String genderCode;	
	
	/**
     * 患者性别
     */
	@JsonProperty(value="genderName")
	@Column(name = "gender_name")
	private String genderName;	
	
	/**
     * 患者出生日期
     */
	@JsonProperty(value="patientBd")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "patient_bd")
	private Date patientBd;	
	
	/**
     * 患者年龄
     */
	@JsonProperty(value="patientAge")
	@Column(name = "patient_age")
	private Integer patientAge;	
	
	/**
     * 病历号
     */
	@JsonProperty(value="mediRecNo")
	@Column(name = "medi_rec_no")
	private String mediRecNo;	
	
	/**
     * 疾病编码
     */
	@JsonProperty(value="pathologyDiagCode")
	@Column(name = "pathology_diag_code")
	private String pathologyDiagCode;	
	
	/**
     * 疾病名称
     */
	@JsonProperty(value="pathologyDiagName")
	@Column(name = "pathology_diag_name")
	private String pathologyDiagName;	
	
	/**
     * 处方开立日期
     */
	@JsonProperty(value="recipelBuildDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "recipel_build_date")
	private Date recipelBuildDate;	
	
	/**
     * 处方有效天数
     */
	@JsonProperty(value="recipelValidDay")
	@Column(name = "recipel_valid_day")
	private Integer recipelValidDay;	
	
	/**
     * 是否处方已过期(0否,1是)
     */
	@JsonProperty(value="isOverdue")
	@Column(name = "is_overdue")
	private Integer isOverdue;	
	
	/**
     * 处方发药药剂师编号
     */
	@JsonProperty(value="recipelDispensingDoctNo")
	@Column(name = "recipel_dispensing_doct_no")
	private String recipelDispensingDoctNo;	
	
	/**
     * 处方发药药剂师签名
     */
	@JsonProperty(value="recipelDispensingDoctName")
	@Column(name = "recipel_dispensing_doct_name")
	private String recipelDispensingDoctName;	
	
	/**
     * 发药时间
     */
	@JsonProperty(value="recipelDispensingDate")
	@Column(name = "recipel_dispensing_date")
	private Date recipelDispensingDate;	
	
	/**
     * 是否已完成取药(0未完成,1已完成)
     */
	@JsonProperty(value="isDone")
	@Column(name = "is_done")
	private Integer isDone;	
	
	/**
     * 是否强制开具处方(0否,1是)
     */
	@JsonProperty(value="isInsist")
	@Column(name = "is_insist")
	private Integer isInsist;	
	
	/**
     * 强制开药理由
     */
	@JsonProperty(value="reason")
	@Column(name = "reason")
	private String reason;	
	
	/**
     * 是否有效数据(0否,1是)
     */
	@JsonProperty(value="isValid")
	@Column(name = "is_valid")
	private Integer isValid;	
	
	/**
     * 数据创建人编号
     */
	@JsonProperty(value="operateCode")
	@Column(name = "operate_code")
	private String operateCode;	
	
	/**
     * 数据创建人
     */
	@JsonProperty(value="operator")
	@Column(name = "operator")
	private String operator;	
	
	/**
     * 数据创建时间
     */
	@JsonProperty(value="operateDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "operate_date")
	private Date operateDate;	
	
	/**
     * 数据更新时间
     */
	@JsonProperty(value="modifyDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "modify_date")
	private Date modifyDate;	
	
	/**
     * 备注  	
     */
	@JsonProperty(value="memo")
	@Column(name = "memo")
	private String memo;	
	
	//非数据库字段
	@Transient
	private String patientBdStr;
	private String recipelBuildDateStr;
	private String recipelDispensingDateStr;
	private String operateDateStr;
	private String modifyDateStr;
}
