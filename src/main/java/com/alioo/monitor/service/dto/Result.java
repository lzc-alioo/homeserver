package com.alioo.monitor.service.dto;

import lombok.Data;

@Data
public class Result {

//"opt": "host_black",
//"fname": "net",
//"function": "set",
//"error": 0

    private String opt;
    private String fname;
    private String function;
    private int error;

}
