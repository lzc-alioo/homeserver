package com.alioo.monitor.controller;

import com.alioo.monitor.router.AccessCtrlRequest;
import com.alioo.monitor.router.FlowStatisticService;
import com.alioo.monitor.router.dto.LbStatisticDto;
import com.alioo.monitor.router.dto.UnavailableTimeDto;
import com.alioo.monitor.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        if(request.getMac()==null){
            return null;
        }
        String token = flowStatisticService.getToken();
        boolean flag = flowStatisticService.accessCtrl(token, request);
        log.info("设置网络结果request:{},flag:{}", JsonUtil.toJson(request), flag);
        return getList();
    }


    /**
     */
    @RequestMapping("/getUnavailableTimeList")
    public List<UnavailableTimeDto> getUnavailableTimeList() {
        System.out.println("main/getUnavailableTimeList.." );

        List<UnavailableTimeDto>  list = flowStatisticService.getUnavailableTimeList();
        log.info("查询禁用时间 list:{}", list);
        return list;
    }


    /**
     * @return
     */
    @RequestMapping("/updateUnavailableTimeList")
    public List<UnavailableTimeDto> updateUnavailableTimeList(@RequestBody List<UnavailableTimeDto> list) {
        System.out.println("main/updateUnavailableTimeList...list:" + JsonUtil.toJson(list));

        flowStatisticService.updateUnavailableTimeList(list);

        log.info("设置禁用时间 list:{}", list);
        return getUnavailableTimeList();
    }


}
