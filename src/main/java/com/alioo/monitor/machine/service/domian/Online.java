package com.alioo.monitor.machine.service.domian;

import lombok.Data;

@Data
public class Online extends NetBase {
    public Online() {
    }

    public Online(String timeStr, long net) {
        super(timeStr, net);
    }
}
