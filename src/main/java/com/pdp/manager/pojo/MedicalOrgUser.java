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

@Getter
@Setter
@Table(name = "t_medical_org_user")
public class MedicalOrgUser{
	
	 /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",unique = true, nullable = false)
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "login_pwd")
    private String loginPwd;

    @Column(name = "tele_phone")
    private String telePhone;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "role")
     private String role;

    @Column(name = "is_valid")
    private Integer isValid;

    @Column(name = "create_date")
    private Date createDate;
    
    @Column(name = "memo")
     private String memo;
    
    @Column(name = "CA_code")
    private String CACode;
    
    @Transient
    private String createDateStr;
}