package com.alioo.monitor.machine.service.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminalStatistic {

    private int onlineTime;
    private int downloadSpeed;
    private int upSpeed;

    private List<Terminal> list;


}
