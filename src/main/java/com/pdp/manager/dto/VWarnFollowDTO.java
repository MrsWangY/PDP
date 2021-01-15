package com.pdp.manager.dto;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VWarnFollowDTO {

	private Integer dataId;
	private String patientName;
	private String patientCardid;
	private String patientTel;
	private String patientAddr;
	private String hospCode;
	private String hospName;
	private Date baseDate;
	private Date nextMonth;
	private Integer diffDays;
	
	@Transient
	private String baseDateStr;
	private String nextMonthStr;
}
