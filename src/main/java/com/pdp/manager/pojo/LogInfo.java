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
 * 业务接口交互操作日志表
 * @author LIXr
 *
 */
@Setter
@Getter
@Table(name = "t_log_info")
public class LogInfo {

	 /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id",unique = true, nullable = false)
    private Integer logId;
	
	/**
	 * 处方主键
	 */
	@Column(name = "data_id")
    private Integer dataId;
	
	/**
     * 处方号（机构代码+处方号）
     */
	@Column(name = "recipe_no")
	private String recipeNo;	
	
	
	/**
     * 操作类型(Y验证处方药品Q强制开药M药房取药)
     */
	@Column(name = "oper_type")
	private String operType;	
	
	/**
     * 操作结果(失败,成功)
     */
	@Column(name = "oper_result")
	private String operResult;	
	
	/**
     * 失败原因(CCYL超出规定用量、CF重复开药、CS参数问题、XT系统问题)
     */
	@Column(name = "fail_reason")
	private String failReason;	
	
	/**
     * 失败次数(同处方失败次数累加（除参数问题/系统原因导致的失败）)
     */
	@Column(name = "fail_times")
	private Integer failTimes;	
	
	/**
     * 是否预警(0否,1是)
     */
	@Column(name = "is_alert")
	private Integer isAlert;	
	
	/**
     * 操作时间
     */
	@Column(name = "oper_date")
	private Date operDate;	
	
	/**
     * 备注
     */
	@Column(name = "memo")
	private String memo;	
	
	//非数据库字段
	@Transient
	private String operTypeStr;
	private String failReasonStr;
	private String operDateStr;
}
