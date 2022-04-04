package com.alioo.monitor.machine.service.domian;

import lombok.Data;

@Data
public class NetOnline extends NetData {
    public NetOnline() {
    }

    public NetOnline(String timeStr, long net) {
        super(timeStr, net);
    }
}
