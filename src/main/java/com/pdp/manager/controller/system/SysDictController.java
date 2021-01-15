package com.pdp.manager.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pdp.manager.service.ISysDictService;

@Controller
@RequestMapping("sysDict")
public class SysDictController {

	@Autowired
	private ISysDictService sysDictService;
	
}
