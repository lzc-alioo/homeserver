package com.alioo.monitor.controller;

import com.alioo.monitor.router.tplink.FlowStatisticService;
import com.alioo.monitor.router.tplink.dto.FlowStatisticDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<FlowStatisticDto> getList() {
        System.out.println("statistic/getList...");


        List<FlowStatisticDto> list = flowStatisticService.getList();
        return list;
    }

    /**
     *
     * @param dto  alias , switchCtrl 必填参数
     * @return
     */
    @RequestMapping("/accessCtrl")
    public List<FlowStatisticDto>  accessCtrl(FlowStatisticDto dto) {
        System.out.println("main/accessCtrl...");


        boolean flag= flowStatisticService.accessCtrl(dto);
        log.info("设置网络结果dto:{},flag:{}",dto,flag);
        return getList();
    }



}
