package com.alioo.monitor.service.dto;


import lombok.Data;

import java.util.List;

@Data
public class Terminal2 implements Comparable<Terminal2> {


    private String name;
    private String ip;
    private String mac;

    private int downSpeed;
    private int upSpeed;

    //第3位是F 可以上网，第3位是T不可上网
    private boolean checked; //true:可以上网；false:不可上网
    private String state; //off_online:离线；on_line:在线
    private int order;

    @Override
    public int compareTo(Terminal2 other) {


        int ret = this.getOrder() - other.getOrder();
        if (ret != 0) {
            return ret;
        }

        return this.getName().compareTo(other.getName());

    }
}
