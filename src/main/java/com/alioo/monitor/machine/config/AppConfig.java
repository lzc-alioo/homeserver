package com.alioo.monitor.machine.config;

import com.alioo.monitor.machine.service.domian.Machine;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AppConfig {


    public static int idx = 1;


    public static Map<String, Machine> MACHINE_MAP = new HashMap<>();

    static {
        MACHINE_MAP.put("B8:FC:9A:3E:6A:DC", new Machine("X3-55", idx++));
        MACHINE_MAP.put("80:9F:9B:CA:7E:38", new Machine("cm201", idx++));
        MACHINE_MAP.put("22:D1:62:75:75:64", new Machine("ipad", idx++));
        MACHINE_MAP.put("70:48:0F:52:ED:C1", new Machine("ali6s", idx++));
        MACHINE_MAP.put("12:42:F5:68:31:34", new Machine("ali6s2", idx++)); //已出售
        MACHINE_MAP.put("48:3C:0C:74:9B:F0", new Machine("hl", idx++));
        MACHINE_MAP.put("80:0C:67:1F:69:F7", new Machine("ali11", idx++));
        MACHINE_MAP.put("26:47:ED:93:9E:38", new Machine("ali11s", idx++));
        MACHINE_MAP.put("A4:83:E7:3C:3F:3D", new Machine("ali13", idx++));
        MACHINE_MAP.put("38:F9:D3:2E:B6:DF", new Machine("ali15", idx++));
        MACHINE_MAP.put("00:F7:6F:9A:4A:A1", new Machine("ali5s", idx++));
        MACHINE_MAP.put("E4:A3:2F:2D:29:00", new Machine("son-w910", idx++));
        MACHINE_MAP.put("00:5B:94:E5:68:A2", new Machine("baichenyu", idx++));
        MACHINE_MAP.put("DC:A6:32:23:35:D4", new Machine("raspberrypi", idx++));
        MACHINE_MAP.put("3C:46:D8:9F:7C:A4", new Machine("raspberrypi-usb", idx++));
        MACHINE_MAP.put("BC:FF:4D:20:11:C2", new Machine("switch1", idx++));
        MACHINE_MAP.put("00:15:8D:F1:00:58", new Machine("switch2", idx++));
        MACHINE_MAP.put("78:0F:77:62:47:E0", new Machine("switch3", idx++));
    }


    public static String getNameAlias(String mac, String defaultName) {
        if (!StringUtils.isEmpty(MACHINE_MAP.get(mac))) {
            return MACHINE_MAP.get(mac).getNameAlias();
        }
        if (!StringUtils.isEmpty(defaultName)) {
            return defaultName;
        }

        return mac.replaceAll("\\:", "");

    }

    public static int getOrder(String mac, int defaultOrder) {

        return Optional.ofNullable(MACHINE_MAP.get(mac)).map(machine -> machine.getOrder()).orElse(defaultOrder);
    }

}
