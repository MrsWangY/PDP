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

@Setter
@Getter
@Table(name = "t_followup_record")
public class FollowupRecord{
	
	/*
	 * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique = true, nullable = false)
    private Integer id;

	/**
	 * 麻卡id
	 */
	@Column(name = "data_id")
    private Integer dataId;

	/**
	 * 随访时间
	 */
	@Column(name = "followup_time")
    private Date followupTime;

	/**
	 * 随访类型
	 */
	@Column(name = "followup_type")
    private String followupType;

	/**
	 * 备注说明
	 */
	@Column(name = "memo")
    private String memo;
	
	@Column(name = "creator_id")
	private Integer creatorId;
	
	@Column(name = "creator")
	private String creator;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "modify_time")
	private Date modifyTime;
	
	@Column(name = "is_valid")
	private Integer isValid;
	
	//非数据库字段
	@Transient
	private String patientName;
    private String patientCardid;
    private String hospCode;
    private String hospName;
    private Integer diffDays;
    private String followupTimeStr;
    private String createTimeStr;
    private String modifyTimeStr;
}