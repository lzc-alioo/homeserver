package com.alioo.monitor.machine.service.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetBase {
    private String timeStr;
    private long net;


    @Override
    public String toString() {
        return "{" +
                "timeStr:'" + timeStr + '\'' +
                ", net:" + net +
                '}';
    }
}
