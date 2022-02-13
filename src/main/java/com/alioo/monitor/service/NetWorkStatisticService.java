package com.alioo.monitor.service;

import com.alioo.monitor.controller.dto.AccessCtrlRequest;
import com.alioo.monitor.controller.dto.NetWorkRequest;
import com.alioo.monitor.service.dto.*;

import java.util.List;

public interface NetWorkStatisticService {

    /**
     * 获得会话token
     *
     * @return
     */
    String getToken();

    /**
     * 获得登录到路由器的机器列表
     *
     * @return
     */
    LbStatisticDto getMachineList();

    /**
     * 获得登录到路由器的机器列表
     *
     * @return
     */
    public List<Terminal2> getMachineList2();

    /**
     * 控制单台机器的上网行为
     *
     * @param request
     * @return
     */
    boolean setNetWorkSwitch(AccessCtrlRequest request);

    /**
     * 不可用的时间列表
     *
     * @return
     */
    List<UnavailableTimeDto> getUnavailableTimeList();

    /**
     * 更新不可用的时间列表
     *
     * @return
     */
    int updateUnavailableTimeList(List<UnavailableTimeDto> list);

    /**
     * 基于时间列表来检查网络是否应该开启/关闭
     */
    void checkNetWork();

    /**
     * 记录上网行为
     */
    void monitorNetWork();

    /**
     * 查询指定机器,指定时间区间的上网数据
     *
     * @param request startTime  示例：202110020000 ;endTime    示例：202110022359
     * @return
     */
    List<NetWorkDetailDto> getNetWorkDetailList(NetWorkRequest request);


    /**
     * 查询指定机器,指定时间区间的联网数据
     *
     * @param request startTime  示例：202110020000 ;endTime    示例：202110022359
     * @return
     */
    List<NetWorkOnLineDto> getNewWorkOnLineList(NetWorkRequest request);


}
