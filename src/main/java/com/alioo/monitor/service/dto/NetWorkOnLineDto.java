package com.alioo.monitor.service.dto;

import lombok.Data;

@Data
public class NetWorkOnLineDto extends NetWorkDto{
    public NetWorkOnLineDto() {
    }

    public NetWorkOnLineDto(String timeStr, long net) {
        super(timeStr, net);
    }
}
