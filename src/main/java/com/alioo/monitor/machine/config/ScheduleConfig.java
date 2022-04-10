package com.alioo.monitor.machine.config;

import com.alioo.monitor.machine.service.MachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleConfig implements SchedulingConfigurer {

    @Autowired
    private MachineService machineService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean
    public Executor taskExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5, new ThreadPoolExecutor.AbortPolicy());
        return scheduledThreadPoolExecutor;
    }


    //3.添加定时任务
    @Scheduled(cron = "0 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void controlNetWork() {
        machineService.controlNetWork();
    }


    //3.添加定时任务
    @Scheduled(cron = "0 * * * * ?")
    private void monitorNetWork() {
        machineService.monitorNetWork();
    }

}
