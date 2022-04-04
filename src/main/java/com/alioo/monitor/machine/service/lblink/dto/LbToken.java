package com.alioo.monitor.machine.service.lblink.dto;

import lombok.Data;

@Data
public class LbToken {

    //{ "opt": "login", "fname": "system", "function": "set", "token": "B65FC5DC95A91524866BBC91B9C20625", "error": 0 }
    private String opt;
    private String fname;
    private String function;
    private String token;
    private int error;

}
