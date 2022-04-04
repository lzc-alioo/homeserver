package com.alioo.monitor.machine.service.lblink.dto;

import lombok.Data;

@Data
public class LbResult {

//"opt": "host_black",
//"fname": "net",
//"function": "set",
//"error": 0

    private String opt;
    private String fname;
    private String function;
    private int error;

}
