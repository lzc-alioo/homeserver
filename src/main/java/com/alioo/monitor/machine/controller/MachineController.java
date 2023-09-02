package com.alioo.monitor.machine.controller;

import com.alioo.monitor.machine.controller.request.AccessControlCommand;
import com.alioo.monitor.machine.controller.request.NetWorkQuery;
import com.alioo.monitor.machine.service.MachineService;
import com.alioo.monitor.machine.service.domian.*;
import com.alioo.monitor.machine.tv.LeTvControl;
import com.alioo.monitor.util.DateTimeUtil;
import com.alioo.monitor.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("machine")
public class MachineController {
    @Value("#{${app.time-control}}")
    private Map<String, String> timeControl;

    @Autowired
    private MachineService machineService;

    public MachineController() {
        log.info("StatisticController init...");
    }


    @RequestMapping("/getMachineList")
    public TerminalStatistic getMachineList() {
        log.info("machine/getMachineList...");

        TerminalStatistic terminalStatistic = machineService.getMachineList();
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
    public List<DisabledTime> getDisabledTimeList(String group) {
        log.info("machine/getDisabledTimeList.. group:{}", group);

        if (ObjectUtils.isEmpty(group)) {
            group = "tv";
        }

        List<DisabledTime> list = machineService.getDisabledTimeList(group);
        log.info("查询禁用时间 list:{}", list);
        return list;
    }


    /**
     * @return
     */
    @RequestMapping("/updateDisabledTimeList")
    public List<DisabledTime> updateUnavailableTimeList(String group, @RequestBody List<DisabledTime> list) {
        log.info("machine/updateDisabledTimeList... group:{} , list:{}", group, JsonUtil.toJson(list));

        machineService.updateDisabledTimeList(group, list);

        log.info("设置禁用时间 list:{}", list);
        return getDisabledTimeList(group);
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
            if (filterNameSet.contains(terminal.getName())) {
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


//    /**
//     * Example: http://127.0.0.1:8081/machine/qs/tv/on
//     * @param group tv,mobile
//     * @param command on/off
//     * @return
//     */
//    @RequestMapping("/qs/{group}/{command}")
//    public String quickStart(@PathVariable(required = false) String group, @PathVariable(required = false) String command) {
//        log.info("quickStart group:{}.command:{}", group, command);
//        if (ObjectUtils.isEmpty(group) || ObjectUtils.isEmpty(command)) {
//            return "false";
//        }
//
//        List<Terminal> machineList2 = getMachineList().getList();
//        Map<String, String> mapIpMap = machineList2.stream().collect(Collectors.toMap(Terminal::getMac, Terminal::getIp));
//
//        Map<String, String> commandMap = new HashMap();
//        commandMap.put("open", "off");
//        commandMap.put("close", "on");
//
//        String macs = timeControl.get(group);
//        if (ObjectUtils.isEmpty(macs)) {
//            log.error("从timeControl中未匹配到 group:{}，timeControl:{}", group, JsonUtil.toJson(timeControl));
//            return "false";
//        }
//
//        Stream.of(macs.split(",")).forEach(mac -> {
//            machineService.accessControl(mac, commandMap.get(command));
//            if ("close".equals(command)) {
//                LeTvControl.sendCommond(mapIpMap.get(mac), 9900, "power");
//            }
//        });
//
////        return "redirect:/main/index";
//        return "redirect:/";
//
//    }


    /**
     * Example: http://127.0.0.1:8081/machine/qs/tv/open
     * Example: http://127.0.0.1:8081/machine/qs/tv/close
     * @param group tv,mobile
     * @param command open/close
     * @return
     */
    @RequestMapping("/qs/{group}/{command}")
    public ModelAndView quickStart(@PathVariable(required = false) String group, @PathVariable(required = false) String command) {
        ModelAndView mv = new ModelAndView();


        log.info("quickStart group:{}.command:{}", group, command);
        if (ObjectUtils.isEmpty(group) || ObjectUtils.isEmpty(command)) {
            mv.addObject("result", false);
            return mv;
        }

        List<Terminal> machineList2 = getMachineList().getList();
        Map<String, String> mapIpMap = machineList2.stream().collect(Collectors.toMap(Terminal::getMac, Terminal::getIp));

        Map<String, String> commandMap = new HashMap();
        commandMap.put("open", "off");
        commandMap.put("close", "on");

        String macs = timeControl.get(group);
        if (ObjectUtils.isEmpty(macs)) {
            log.error("从timeControl中未匹配到 group:{}，timeControl:{}", group, JsonUtil.toJson(timeControl));
            mv.addObject("result", false);
            return mv;        }

        Stream.of(macs.split(",")).forEach(mac -> {
            machineService.accessControl(mac, commandMap.get(command));
            if ("close".equals(command)) {
                LeTvControl.sendCommond(mapIpMap.get(mac), 9900, "power");
            }
        });

        mv.addObject("result", true);
        mv.setViewName("redirect:/");
        return mv;
    }

}
