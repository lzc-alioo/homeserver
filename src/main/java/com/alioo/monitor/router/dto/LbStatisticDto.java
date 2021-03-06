package com.alioo.monitor.router.dto;

import lombok.Data;

import java.util.List;

@Data
public class LbStatisticDto {

//"opt": "main",
//"fname": "system",
//"function": "get",

//"total_speed": 12800,
//"total_upspeed": 12800,
//"connected": true,
//"ontime": 1388543,
//"cur_speed": 0,
//"up_speed": 0,
//"down_bytes": 101085490,
//"up_bytes": 24322033,

    private String opt;
    private String fname;
    private String function;
    private int error;

    private int totalSpeed;
    private int totalUpspeed;
    private boolean connected;
    private int ontime;
    private int curSpeed;
    private int upSpeed;
    private int downBytes;
    private int upBytes;

    private List<Terminal> terminals;


}
