package com.pdp.manager.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
@Table(name = "t_medical_org")
public class MedicalOrg implements Serializable {
	
	@Id
    @Column(name = "org_code",unique = true, nullable = false)
    private String orgCode;

	@Column(name = "org_name")
	private String orgName;

	@Column(name = "province_code")
	private String provinceCode;
	
	@Column(name = "province")
	private String province;
	
	@Column(name = "city_code")
	private String cityCode;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "area_code")
	private String areaCode;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "org_addr")
	private String orgAddr;
	
	@Column(name = "is_valid")
	private Integer isValid;

	@Column(name = "memo")
	private String memo;

	private static final long serialVersionUID = 1L;
	
	//非数据库字段
	@Transient 
	private String oper;
	private String userNum; //医疗机构下的分配人数
	private String caseNum; //医疗机构下的麻卡登记数

}