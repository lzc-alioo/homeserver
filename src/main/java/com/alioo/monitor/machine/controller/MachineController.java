package com.alioo.monitor.machine.controller;

import com.alioo.monitor.machine.controller.request.AccessControlCommand;
import com.alioo.monitor.machine.controller.request.NetWorkQuery;
import com.alioo.monitor.machine.service.MachineService;
import com.alioo.monitor.machine.service.domian.DisabledTime;
import com.alioo.monitor.machine.service.domian.NetDetail;
import com.alioo.monitor.machine.service.domian.NetOnline;
import com.alioo.monitor.machine.service.domian.Terminal;
import com.alioo.monitor.util.DateTimeUtil;
import com.alioo.monitor.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<Terminal> getMachineList() {
        log.info("machine/getMachineList...");

        List<Terminal> list = machineService.getMachineList();
        return list;
    }

    /**
     * @return
     */
    @RequestMapping("/accessControl")
    public List<Terminal> accessControl(AccessControlCommand request) {
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


    @RequestMapping("/getNetDetailList")
    public List<NetDetail> getNetWorkData(NetWorkQuery request) {

        checkParams(request);

        List<NetDetail> list = machineService.getNetDetailList(request);

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


    @RequestMapping("/getNetOnlineList")
    public List<NetOnline> getNetOnlineList(NetWorkQuery request) {

        checkParams(request);

        List<NetOnline> list = machineService.getNetOnlineList(request);

        return list;

    }


}
