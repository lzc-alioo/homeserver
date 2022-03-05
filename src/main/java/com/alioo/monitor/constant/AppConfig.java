package com.alioo.monitor.constant;

import com.alioo.monitor.service.dto.Machine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AppConfig {


//    public static Map<String, String> NAME_MAP = new HashMap<>();
//
//    static {
//        NAME_MAP.put("80:0C:67:1F:69:F7", "ali11");
//        NAME_MAP.put("26:47:ED:93:9E:38", "ali11-si"); //ali11私有地址
//        NAME_MAP.put("DC:A6:32:23:35:D4", "raspberrypi");
//        NAME_MAP.put("48:3C:0C:74:9B:F0", "hl");
//        NAME_MAP.put("A4:83:E7:3C:3F:3D", "ali13");
//        NAME_MAP.put("78:0F:77:62:47:E0", "kaiguan01");
//        NAME_MAP.put("38:F9:D3:2E:B6:DF", "ali15");
//        NAME_MAP.put("70:48:0F:52:ED:C1", "ali6s");
//        NAME_MAP.put("BC:FF:4D:20:11:C2", "mindor");
//        NAME_MAP.put("00:5B:94:E5:68:A2", "baichenxu");
//        NAME_MAP.put("B8:FC:9A:3E:6A:DC", "X3-55");
//        NAME_MAP.put("00:F7:6F:9A:4A:A1", "ali5s");
//        NAME_MAP.put("E4:A3:2F:2D:29:00", "W910");
//        NAME_MAP.put("3C:46:D8:9F:7C:A4", "raspberrypi-usbwifi");
//        NAME_MAP.put("00:15:8D:F1:00:58", "0058");
//    }

    public static int idx = 1;


//    public static Map<String, Integer> orderdMacMap = new HashMap<>();
//    static {
//        orderdMacMap.put("B8:FC:9A:3E:6A:DC", idx++); //X3-55
//        orderdMacMap.put("80:9F:9B:CA:7E:38", idx++); //CM201-2
//        orderdMacMap.put("70:48:0F:52:ED:C1", idx++); //ali6s
//        orderdMacMap.put("DC:A6:32:23:35:D4", idx++); //raspberrypi
//        orderdMacMap.put("48:3C:0C:74:9B:F0", idx++); //hlold
//        orderdMacMap.put("C2:31:3A:DD:51:35", idx++); //hlnew50
//        orderdMacMap.put("38:F9:D3:2E:B6:DF", idx++); //alioo15
//        orderdMacMap.put("A4:83:E7:3C:3F:3D", idx++); //E097815-BJ
//        orderdMacMap.put("BC:FF:4D:20:11:C2", idx++); //mindor
//        orderdMacMap.put("00:5B:94:E5:68:A2", idx++); //baichenxu
//
//    }


    public static Map<String, Machine> MACHINE_MAP = new HashMap<>();

    static {
        MACHINE_MAP.put("B8:FC:9A:3E:6A:DC", new Machine("X3-55", idx++));
        MACHINE_MAP.put("80:9F:9B:CA:7E:38", new Machine("cm201", idx++));
        MACHINE_MAP.put("70:48:0F:52:ED:C1", new Machine("ali6s", idx++));
        MACHINE_MAP.put("80:0C:67:1F:69:F7", new Machine("ali11", idx++));
        MACHINE_MAP.put("26:47:ED:93:9E:38", new Machine("ali11s", idx++));
        MACHINE_MAP.put("48:3C:0C:74:9B:F0", new Machine("hl", idx++));
        MACHINE_MAP.put("A4:83:E7:3C:3F:3D", new Machine("ali13", idx++));
        MACHINE_MAP.put("38:F9:D3:2E:B6:DF", new Machine("ali15", idx++));
        MACHINE_MAP.put("00:F7:6F:9A:4A:A1", new Machine("ali5s", idx++));
        MACHINE_MAP.put("E4:A3:2F:2D:29:00", new Machine("son-w910", idx++));
        MACHINE_MAP.put("00:5B:94:E5:68:A2", new Machine("baichenxu", idx++));
        MACHINE_MAP.put("DC:A6:32:23:35:D4", new Machine("raspberrypi", idx++));
        MACHINE_MAP.put("3C:46:D8:9F:7C:A4", new Machine("raspberrypi-usb", idx++));
        MACHINE_MAP.put("BC:FF:4D:20:11:C2", new Machine("switch1", idx++));
        MACHINE_MAP.put("00:15:8D:F1:00:58", new Machine("switch2", idx++));
        MACHINE_MAP.put("78:0F:77:62:47:E0", new Machine("switch3", idx++));
    }


    public static String getNameAlias(String mac, String defaultName) {

        return Optional.ofNullable(MACHINE_MAP.get(mac)).map(machine -> machine.getNameAlias()).orElse(defaultName);
    }

    public static int getOrder(String mac, int defaultOrder) {

        return Optional.ofNullable(MACHINE_MAP.get(mac)).map(machine -> machine.getOrder()).orElse(defaultOrder);
    }

}
