package com.pdp.manager.redis;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * @author LIXr
 * @date 2020/12/10
 */
@Order(1)
@Component
public class StartRunner implements CommandLineRunner {
 
    @Resource
    private InitStartService initStartService;
 
    @Override
    public void run(String... args) {
        initStartService.initData();
    }


}
