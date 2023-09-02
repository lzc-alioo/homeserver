package com.alioo.monitor.machine.service.domian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Machine {
    private String mac;

    private String nameAlias;

    private int order;

    /**
     * ip可能会变化
     */
    private String ip;

    public Machine(String mac, String nameAlias, int order) {
        this.mac = mac;
        this.nameAlias = nameAlias;
        this.order = order;
    }

}
