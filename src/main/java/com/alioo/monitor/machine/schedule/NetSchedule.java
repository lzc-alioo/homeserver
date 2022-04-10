//package com.alioo.monitor.machine.schedule;
//
//
//import com.alioo.monitor.machine.service.MachineService;
//import com.alioo.monitor.util.DateTimeUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.Date;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class NetSchedule {
//
//    @Autowired
//    private MachineService machineService;
//
//    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5, new ThreadPoolExecutor.AbortPolicy());
//
//    @PostConstruct
//    private void init() {
////        下一分钟的00秒开始执行
////        String nextMinuteStr=DateTimeUtil.toDateTimeString( DateTimeUtil.getNextDateTimeBySecond(new Date(), 60),"yyyy-MM-dd HH:mm:00");
////        Date nextMinute=DateTimeUtil.toDateFromStr(nextMinuteStr);
//
//        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
//            machineService.controlNetWork();
//        }, 10L, 60L, TimeUnit.SECONDS);
//
//
//        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
//            machineService.monitorNetWork();
//        }, 20L, 60L, TimeUnit.SECONDS);
//
//    }
//
//
//}
