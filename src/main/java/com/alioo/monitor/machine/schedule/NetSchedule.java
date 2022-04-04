package com.alioo.monitor.machine.schedule;


import com.alioo.monitor.machine.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class NetSchedule {

    @Autowired
    private MachineService machineService;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5, new ThreadPoolExecutor.AbortPolicy());

    @PostConstruct
    private void init() {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            machineService.controlNetWork();
        }, 10L, 60L, TimeUnit.SECONDS);


        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            machineService.monitorNetWork();
        }, 20L, 60L, TimeUnit.SECONDS);

    }


}
