package com.alioo.monitor.machine.service.domian;

import lombok.Data;

@Data
public class Net extends NetBase {
    public Net() {
    }

    public Net(String timeStr, long net) {
        super(timeStr, net);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
