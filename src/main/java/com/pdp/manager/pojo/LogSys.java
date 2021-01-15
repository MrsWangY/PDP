package com.pdp.manager.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
/**
 * 麻卡系统操作日志表
 * @author LIXr
 *
 */
@Getter
@Setter
public class LogSys{
	
	/**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id",unique = true, nullable = false)
    private Integer logId;

	@Column(name = "oper_module")
    private String operModule;

	@Column(name = "oper_type")
    private String operType;

	@Column(name = "oper_data_id")
    private String operDataId;

    @Column(name = "oper_content")
    private String operContent;

    @Column(name = "oper_result")
    private String operResult;

    @Column(name = "fail_reason")
    private String failReason;

    @Column(name = "oper_user_id")
    private Integer operUserId;

    @Column(name = "oper_user")
    private String operUser;

    @Column(name = "oper_date")
    private Date operDate;

    @Column(name = "memo")
    private String memo;
    
    @Transient
    private String operDateStr;
    
    public LogSys(){
    	
    }
    
    public LogSys(String operModule,String operType,String operDataId,String operContent,
			String operResult,String failReason,Integer operUserId,String operUser,
			Date operDate,String memo){
    	this.operModule = operModule;
    	this.operType = operType;
    	this.operDataId = operDataId;
    	this.operContent = operContent;
    	this.operResult = operResult;
    	this.failReason = failReason;
    	this.operUserId = operUserId;
    	this.operUser = operUser;
    	this.operDate = operDate;
    	this.memo = memo;
    }
}