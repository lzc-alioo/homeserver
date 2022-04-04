package com.alioo.monitor.machine.controller.request;

import lombok.Data;

@Data
public class AccessControlCommand {

    /**
     * 机器MAC地址
     */
    private String mac;

    /**
     * off可以上网 on不可上网
     */
    private String act;


}
