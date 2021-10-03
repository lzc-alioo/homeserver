package com.alioo.monitor.controller.dto;

import lombok.Data;

@Data
public class AccessCtrlRequest {

    /**
     * 机器MAC地址
     */
    private String mac;

    /**
     * off可以上网 on不可上网
     */
    private String act;


}
