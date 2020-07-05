package com.alioo.monitor.router;

import lombok.Data;

@Data
public class AccessCtrlRequest {

    private String mac;
    private String act; // off可以上网 on不可上网



}
