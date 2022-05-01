package com.alioo.monitor.machine.controller;

import com.alioo.monitor.machine.controller.request.AccessControlCommand;
import com.alioo.monitor.machine.controller.request.NetWorkQuery;
import com.alioo.monitor.machine.service.MachineService;
import com.alioo.monitor.machine.service.domian.*;
import com.alioo.monitor.util.DateTimeUtil;
import com.alioo.monitor.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("machine")
public class MachineController {

    @Autowired
    private MachineService machineService;

    public MachineController() {
        log.info("StatisticController init...");
    }


    @RequestMapping("/getMachineList")
    public TerminalStatistic getMachineList() {
        log.info("machine/getMachineList...");

        TerminalStatistic  terminalStatistic = machineService.getMachineList();
        return terminalStatistic;
    }

    /**
     * @return
     */
    @RequestMapping("/accessControl")
    public TerminalStatistic accessControl(AccessControlCommand request) {
        log.info("machine/accessControl...request:" + JsonUtil.toJson(request));
        if (request.getMac() == null) {
            return null;
        }
        boolean flag = machineService.accessControl(request);
        log.info("设置网络结果request:{},flag:{}", JsonUtil.toJson(request), flag);
        return getMachineList();
    }


    /**
     *
     */
    @RequestMapping("/getDisabledTimeList")
    public List<DisabledTime> getDisabledTimeList() {
        System.out.println("machine/getDisabledTimeList..");

        List<DisabledTime> list = machineService.getDisabledTimeList();
        log.info("查询禁用时间 list:{}", list);
        return list;
    }


    /**
     * @return
     */
    @RequestMapping("/updateDisabledTimeList")
    public List<DisabledTime> updateUnavailableTimeList(@RequestBody List<DisabledTime> list) {
        System.out.println("machine/updateDisabledTimeList...list:" + JsonUtil.toJson(list));

        machineService.updateDisabledTimeList(list);

        log.info("设置禁用时间 list:{}", list);
        return getDisabledTimeList();
    }


    @RequestMapping("/getMonitorMachineList")
    public List<Terminal> getMonitorMachineList() {
        log.info("machine/getMonitorMachineList...");

        List<Terminal> list = machineService.getMachineList().getList();


        Set<String> filterNameSet = new HashSet<>();
        filterNameSet.add("switch1");
        filterNameSet.add("switch2");
        filterNameSet.add("raspberrypi");
        filterNameSet.add("raspberrypi-usb");

        list = list.stream().filter(terminal -> {
            if(filterNameSet.contains(terminal.getName())){
                return false;
            }
            return true;

        }).collect(Collectors.toList());

        return list;
    }


    @RequestMapping("/getNetList")
    public List<Net> getNetList(NetWorkQuery request) {

        checkParams(request);

        List<Net> list = machineService.getNetList(request);

        return list;
    }

    private void checkParams(NetWorkQuery request) {
        String startTime = request.getStartTime();
        if (StringUtils.isEmpty(startTime)) {
            request.setStartTime(DateTimeUtil.getDateTimeString("yyyyMMdd") + "0000");
        }
        String endTime = request.getEndTime();
        if (StringUtils.isEmpty(endTime)) {
            request.setEndTime(DateTimeUtil.getDateTimeString("yyyyMMdd") + "2359");
        }
        String machineName = request.getMachineName();
        if (StringUtils.isEmpty(machineName)) {
            request.setMachineName("X3-55");
        }
    }


    @RequestMapping("/getOnlineList")
    public List<Online> getOnlineList(NetWorkQuery request) {

        checkParams(request);

        List<Online> list = machineService.getOnlineList(request);

        return list;

    }


}
