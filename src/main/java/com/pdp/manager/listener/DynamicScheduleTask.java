package com.pdp.manager.listener;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.pdp.manager.common.utils.StringHelpers;
import com.pdp.manager.service.IMedicalCaseService;


@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {

    @Mapper
    public interface CronMapper {
        @Select("select cron from cron limit 1")
        public String getCron();
    }

    @Autowired      //注入mapper
    @SuppressWarnings("all")
    CronMapper cronMapper;
    @Autowired
    private IMedicalCaseService medicalCaseService;

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> medicalCaseService.autoCancellation(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getCron();
                    //2.2 合法性校验.
                    if (!StringHelpers.isNotEmpty(cron)) {
                    	// Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
//    每隔5秒执行一次：*/5 * * * * ?
//
//            每隔1分钟执行一次：0 */1 * * * ?
//
//            每天23点执行一次：0 0 23 * * ?
//
//            每天凌晨1点执行一次：0 0 1 * * ?
//
//            每月1号凌晨1点执行一次：0 0 1 1 * ?
//
//            每月最后一天23点执行一次：0 0 23 L * ?
//
//            每周星期天凌晨1点实行一次：0 0 1 ? * L
//
//            在26分、29分、33分执行一次：0 26,29,33 * * * ?
//
//            每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
}