package com.alioo.monitor.schedule;


import com.alioo.monitor.service.NetWorkStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class NetSchedule {

    @Autowired
    private NetWorkStatisticService flowStatisticService;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5, new ThreadPoolExecutor.AbortPolicy());

    @PostConstruct
    private void init() {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            flowStatisticService.checkNetWork();
        }, 10L, 60L, TimeUnit.SECONDS);


        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            flowStatisticService.monitorNetWork();
        }, 20L, 60L, TimeUnit.SECONDS);

    }


}
