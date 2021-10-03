package com.alioo.monitor.service.dto;

import lombok.Data;

@Data
public class FlowStatisticDto {

    private int id;
    private String mac;
    private String ip;
    private String alias;
    private long downloadTotal;
    private long uploadTotal;
    private long downloadSpeed;
    private long uploadSpeed;

    //0 可以上网 ；1 不可以上网 ; null 可以上网，但是不能设置
    private Integer switchCtrl;

}
