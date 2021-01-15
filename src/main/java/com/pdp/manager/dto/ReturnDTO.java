package com.pdp.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnDTO {

	private String name;
	
	private Integer days;
	
	public ReturnDTO(){}
	
	public ReturnDTO(String drugName,Integer maxDays){
		this.name = drugName;
		this.days = maxDays;
	}
}
