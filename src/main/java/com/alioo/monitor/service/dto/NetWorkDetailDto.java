package com.alioo.monitor.service.dto;

import lombok.Data;

@Data
public class NetWorkDetailDto extends NetWorkDto {
    public NetWorkDetailDto() {
    }

    public NetWorkDetailDto(String timeStr, long net) {
        super(timeStr, net);
    }
}
