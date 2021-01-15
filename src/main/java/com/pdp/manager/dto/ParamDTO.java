package com.pdp.manager.dto;

import java.util.List;

import com.pdp.manager.pojo.Recipel;
import com.pdp.manager.pojo.RecipelDrug;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParamDTO {

	public Recipel recipel = null;
	public List<RecipelDrug> rdList = null;
}
