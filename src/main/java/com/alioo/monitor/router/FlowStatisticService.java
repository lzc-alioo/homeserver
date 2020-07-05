package com.alioo.monitor.router;

import com.alioo.monitor.router.dto.FlowStatisticDto;
import com.alioo.monitor.router.dto.LbStatisticDto;

import java.util.List;

public interface FlowStatisticService {

    public String getToken();

    public LbStatisticDto getList();


    public boolean accessCtrl(String token, AccessCtrlRequest request);


}
