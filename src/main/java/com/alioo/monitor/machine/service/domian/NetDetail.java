package com.alioo.monitor.machine.service.domian;

import lombok.Data;

@Data
public class NetDetail extends NetData {
    public NetDetail() {
    }

    public NetDetail(String timeStr, long net) {
        super(timeStr, net);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
