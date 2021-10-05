package com.alioo.monitor.controller;

import com.alioo.monitor.controller.dto.AccessCtrlRequest;
import com.alioo.monitor.service.NetWorkStatisticService;
import com.alioo.monitor.controller.dto.NetWorkDataRequest;
import com.alioo.monitor.service.dto.LbStatisticDto;
import com.alioo.monitor.service.dto.NetWorkDataDto;
import com.alioo.monitor.service.dto.UnavailableTimeDto;
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
@RequestMapping("statistic")
public class NetWorkStatisticController {

    @Autowired
    private NetWorkStatisticService netWorkStatisticService;

    public NetWorkStatisticController() {
        log.info("StatisticController init...");
    }

    @RequestMapping("/getMachineList")
    public LbStatisticDto getMachineList() {
        log.info("statistic/getList...");


        LbStatisticDto list = netWorkStatisticService.getMachineList();
        return list;
    }

    /**
     * @return
     */
    @RequestMapping("/setNetWorkSwitch")
    public LbStatisticDto setNetWorkSwitch(AccessCtrlRequest request) {
        log.info("statistic/setNetWorkSwitch...request:" + JsonUtil.toJson(request));
        if (request.getMac() == null) {
            return null;
        }
        boolean flag = netWorkStatisticService.setNetWorkSwitch(request);
        log.info("设置网络结果request:{},flag:{}", JsonUtil.toJson(request), flag);
        return getMachineList();
    }


    /**
     *
     */
    @RequestMapping("/getUnavailableTimeList")
    public List<UnavailableTimeDto> getUnavailableTimeList() {
        System.out.println("main/getUnavailableTimeList..");

        List<UnavailableTimeDto> list = netWorkStatisticService.getUnavailableTimeList();
        log.info("查询禁用时间 list:{}", list);
        return list;
    }


    /**
     * @return
     */
    @RequestMapping("/updateUnavailableTimeList")
    public List<UnavailableTimeDto> updateUnavailableTimeList(@RequestBody List<UnavailableTimeDto> list) {
        System.out.println("main/updateUnavailableTimeList...list:" + JsonUtil.toJson(list));

        netWorkStatisticService.updateUnavailableTimeList(list);

        log.info("设置禁用时间 list:{}", list);
        return getUnavailableTimeList();
    }


    @RequestMapping("/getNetWorkData")
    public List<NetWorkDataDto> getNetWorkData(NetWorkDataRequest request) {

        String startTime = request.getStartTime();
        if (StringUtils.isEmpty(startTime)) {
            startTime = DateTimeUtil.getDateTimeString("yyyyMMdd") + "0000";
        }


        String endTime = request.getEndTime();
        if (StringUtils.isEmpty(endTime)) {

            endTime = DateTimeUtil.getDateTimeString("yyyyMMdd") + "2359";
        }

        String machineName = request.getMachineName();
        if (StringUtils.isEmpty(machineName)) {
            machineName = "X3-55";
        }

        List<NetWorkDataDto> list = netWorkStatisticService.getNetWorkData(startTime, endTime, machineName);

        return list;

    }


    @RequestMapping("/getOnLineData")
    public List<NetWorkDataDto> getOnLineData(NetWorkDataRequest request) {

        String startTime = request.getStartTime();
        if (StringUtils.isEmpty(startTime)) {
            startTime = DateTimeUtil.getDateTimeString("yyyyMMdd") + "0000";
        }


        String endTime = request.getEndTime();
        if (StringUtils.isEmpty(endTime)) {

            endTime = DateTimeUtil.getDateTimeString("yyyyMMdd") + "2359";
        }

        String machineName = request.getMachineName();
        if (StringUtils.isEmpty(machineName)) {
            machineName = "X3-55";
        }

        List<NetWorkDataDto> list = netWorkStatisticService.getOnLineData(startTime, endTime, machineName);

        return list;

    }


}
