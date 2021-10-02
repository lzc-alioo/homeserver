package com.alioo.monitor.router;

import com.alioo.monitor.router.dto.FlowStatisticDto;
import com.alioo.monitor.router.dto.LbStatisticDto;
import com.alioo.monitor.router.dto.NetWorkDataDto;
import com.alioo.monitor.router.dto.UnavailableTimeDto;

import java.util.List;

public interface FlowStatisticService {

    public String getToken();

    public LbStatisticDto getList();

    public boolean accessCtrl(AccessCtrlRequest request);


    public List<UnavailableTimeDto> getUnavailableTimeList();

    public int updateUnavailableTimeList(List<UnavailableTimeDto> list);

    public void checkNetWork();

    public void monitorNetWork();

    public List<NetWorkDataDto> netWorkData(String datestr, String machineName);
}
