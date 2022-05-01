package com.alioo.monitor.machine.service;

import com.alioo.monitor.machine.controller.request.AccessControlCommand;
import com.alioo.monitor.machine.controller.request.NetWorkQuery;
import com.alioo.monitor.machine.service.domian.*;

import java.util.List;

public interface MachineService {

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
    TerminalStatistic getMachineList();

    /**
     * 根据mac地址控制单台机器的上网行为
     *
     * @param controlCommand
     * @return
     */
    boolean accessControl(AccessControlCommand controlCommand);

    /**
     * 禁用上网的时间列表
     *
     * @return
     */
    List<DisabledTime> getDisabledTimeList();

    /**
     * 更新不可用的时间列表
     *
     * @return
     */
    int updateDisabledTimeList(List<DisabledTime> list);

    /**
     * 基于「禁用上网的时间列表」来检查网络是否应该开启/关闭
     */
    void controlNetWork();

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
    List<Net> getNetList(NetWorkQuery request);


    /**
     * 查询指定机器,指定时间区间的联网数据
     *
     * @param request startTime  示例：202110020000 ;endTime    示例：202110022359
     * @return
     */
    List<Online> getOnlineList(NetWorkQuery request);


}
