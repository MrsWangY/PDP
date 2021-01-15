package com.pdp.manager.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "t_sys_dict")
public class SysDict{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique = true, nullable = false)
    private Integer id;

	@Column(name = "code")
    private String code;

	@Column(name = "name")
    private String name;

	@Column(name = "descript")
    private String descript;

	@Column(name = "is_valid")
    private Integer isValid;

	@Column(name = "create_time")
    private Date createTime;

	@Column(name = "creator_id")
    private Integer creatorId;

	@Column(name = "creator")
    private String creator;

	@Column(name = "is_del")
    private Integer isDel;

}