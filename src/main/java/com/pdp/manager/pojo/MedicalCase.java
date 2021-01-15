package com.pdp.manager.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "t_medical_case")
public class MedicalCase implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id",unique = true, nullable = false)
    private Integer dataId;

	/**
	 * 病历编号
	 */
	@Column(name = "case_num")
    private String caseNum;

	/**
	 * 患者姓名
	 */
	@Column(name = "patient_name")
    private String patientName;

	/**
	 * 性别代码
	 */
	@Column(name = "patient_gender_code")
    private String patientGenderCode;

    /**
	 * 性别
	 */
	@Column(name = "patient_gender_name")
    private String patientGenderName;

    /**
	 * 出生日期
	 */
	@Column(name = "patient_birthday")
    private Date patientBirthday;

	/**
	 * 年龄
	 */
	@Column(name = "patient_age")
	private Integer patientAge;
	
    /**
	 * 联系电话
	 */
	@Column(name = "patient_tel")
    private String patientTel;

    /**
	 * 身份证号
	 */
	@Column(name = "patient_cardid")
    private String patientCardid;

    /**
	 * 婚姻状况代码
	 */
	@Column(name = "marital_code")
    private String maritalCode;

    /**
	 * 婚姻
	 */
	@Column(name = "marital_name")
    private String maritalName;

    /**
	 * 职业类别代码
	 */
	@Column(name = "occup_code")
    private String occupCode;

	/**
	 * 职业
	 */
	 @Column(name = "occup_name")
    private String occupName;

    /**
	 * 民族代码
	 */
	@Column(name = "nation_code")
    private String nationCode;

    /**
	 * 民族
	 */
	@Column(name = "nation_name")
    private String nationName;

    /**
	 * 诊断编码
	 */
	@Column(name = "diag_code")
    private String diagCode;

    /**
	 * 诊断
	 */
	@Column(name = "diag_name")
    private String diagName;

    /**
	 * 诊断时间
	 */
	@Column(name = "diag_time")
    private Date diagTime;
	
    /**
	 * 药物过敏史
	 */
	@Column(name = "drug_allergic")
    private String drugAllergic;

    /**
	 * 麻醉药物使用史
	 */
	 @Column(name = "drug_usage_history")
    private String drugUsageHistory;

    /**
	 * 家庭住址
	 */
	@Column(name = "patient_addr")
    private String patientAddr;

	/**
	 * 工作单位
	 */
	@Column(name = "patient_unit_addr")
    private String patientUnitAddr;

    /**
	 * 代办人姓名
	 */
	@Column(name = "contactor_name")
    private String contactorName;

    /**
	 * 代办人性别代码
	 */
	@Column(name = "contactor_gender_code")
    private String contactorGenderCode;

    /**
	 * 代办人性别
	 */
	@Column(name = "contactor_gender_name")
    private String contactorGenderName;

    /**
	 * 代办人年龄
	 */
	@Column(name = "contactor_age")
    private Integer contactorAge;

    /**
	 * 与患者关系代码
	 */
	@Column(name = "contactor_type_code")
    private String contactorTypeCode;

    /**
	 * 与患者关系
	 */
	@Column(name = "contactor_type_name")
    private String contactorTypeName;

    /**
	 * 代办人联系电话
	 */
	@Column(name = "contactor_tel")
    private String contactorTel;

    /**
	 * 代办人身份证号
	 */
	@Column(name = "contactor_cardid")
    private String contactorCardid;

	/**
	 * 代办人家庭住址
	 */
	@Column(name = "contactor_addr")
    private String contactorAddr;

    /**
	 * 代办人工作单位
	 */
	@Column(name = "contactor_unit_addr")
    private String contactorUnitAddr;

    /**
	 * 供药医疗结构代码
	 */
	@Column(name = "hosp_code")
    private String hospCode;

    /**
	 * 供药医疗机构名称
	 */
	@Column(name = "hosp_name")
    private String hospName;

    /**
	 * 发卡机构代码
	 */
	@Column(name = "give_card_org_code")
    private String giveCardOrgCode;

    /**
	 * 发卡机构
	 */
	 @Column(name = "give_card_org_name")
    private String giveCardOrgName;

	/**
	  * 发卡日期
	  */
	@Column(name = "give_card_date")
	private Date giveCardDate;
	
    /**
	 * 建档日期
	 */
	@Column(name = "file_date")
    private Date fileDate;

    /**
	 * 转诊日期
	 */
	@Column(name = "referral_date")
    private Date referralDate;
	
	/**
	 * 转诊原因
	 */
	@Column(name = "referral_reason")
    private String referralReason;
    
    /**
	 * 是否注销(0未注销,1已注销)
	 */
	@Column(name = "is_cancellation")
    private Integer isCancellation;

    /**
	 * 注销日期
	 */
	@Column(name = "cancellation_date")
    private Date cancellationDate;
	
	/**
	 * 注销类型：手动注销、自动注销
	 */
	@Column(name = "cancellation_type")
	private String cancellationType;
	
	/**
	 * 注销原因：患者死亡、转诊其他医疗机构、其他、
	 */
	@Column(name = "cancellation_reason")
	private String cancellationReason;
	
    private static final long serialVersionUID = 1L;
    
    //非数据库字段
    @Transient
    private String patientBirthdayStr;
    private String diagTimeStr;
    private String giveCardDateStr;
    private String fileDateStr;
    private String referralDateStr;
    private String cancellationDateStr;
    
}