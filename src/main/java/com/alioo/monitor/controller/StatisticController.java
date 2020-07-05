package com.alioo.monitor.controller;

import com.alioo.monitor.router.AccessCtrlRequest;
import com.alioo.monitor.router.FlowStatisticService;
import com.alioo.monitor.router.dto.LbStatisticDto;
import com.alioo.monitor.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("statistic")
public class StatisticController {

    @Autowired
    private FlowStatisticService flowStatisticService;

    public StatisticController() {
        System.out.println("StatisticController...");
    }

    @RequestMapping("/getList")
    public LbStatisticDto getList() {
        System.out.println("statistic/getList...");


        LbStatisticDto list = flowStatisticService.getList();
        return list;
    }

    /**
     * @return
     */
    @RequestMapping("/accessCtrl")
    public LbStatisticDto accessCtrl(AccessCtrlRequest request) {
        System.out.println("main/accessCtrl...request:" + JsonUtil.toJson(request));

        String token = flowStatisticService.getToken();
        boolean flag = flowStatisticService.accessCtrl(token, request);
        log.info("设置网络结果request:{},flag:{}", JsonUtil.toJson(request), flag);
        return getList();
    }


}
