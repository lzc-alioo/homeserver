package com.alioo.monitor.router.dto;


import lombok.Data;

import java.util.List;

@Data
public class Terminal implements Comparable<Terminal>{

//"mac": "80:0C:67:1F:69:F7",
//"ip": "192.168.16.225",

//"speed": 0,
//"up_speed": 0,
//"up_bytes": 441102,
//"down_bytes": 4101267,

//"name": "ali11",
//"vendor": 0,
//"ostype": 5,
//"uptime": 740896,
//"ontime": 393,
//"mode": 0,

//"pic": 0,
//"flag": "FTFFFFTFFTFF",
//"sig": 65,
//"apps": []


    private String mac;
    private String ip;

    private int speed;
    private int upSpeed;
    private int upBytes;
    private int downBytes;

    private String name;
    private String vendor;
    private int ostype;
    private int uptime;
    private int ontime;
    private int mode;

    private int pic;
    private String flag;
    private int sig;

    private List<String> apps;

    private int order;

    @Override
    public int compareTo(Terminal other) {


        return this.getOrder()-other.getOrder();
    }
}
