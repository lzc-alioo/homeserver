package com.alioo.monitor.constant;

import java.util.HashMap;
import java.util.Map;

//@Component
public class AppConfig {

    public static Map<String,String> whiteMacMap=new HashMap<>();

    static {
        whiteMacMap.put("80:0C:67:1F:69:F7","ali11");
        whiteMacMap.put("DC:A6:32:23:35:D4","raspberrypi");
//        whiteMacMap.put("48:3C:0C:74:9B:F0","hl");
        whiteMacMap.put("A4:83:E7:3C:3F:3D","ali13");
        whiteMacMap.put("78:0F:77:62:47:E0","kaiguan01");
        whiteMacMap.put("38:F9:D3:2E:B6:DF","ali15");
        whiteMacMap.put("70:48:0F:52:ED:C1","ali6s");
//        whiteMacMap.put("B8:FC:9A:3E:6A:DC","X3-55");
//        whiteMacMap.put("00:F7:6F:9A:4A:A1","ali5s");
//        whiteMacMap.put("E4:A3:2F:2D:29:00","W910");
//        whiteMacMap.put("3C:46:D8:9F:7C:A4","raspberrypi-usbwifi");
    }


    public static Map<String,Integer> orderdMacMap=new HashMap<>();

    public static int idx=1;

    static {
        orderdMacMap.put("B8:FC:9A:3E:6A:DC",idx++); //X3-55
        orderdMacMap.put("80:9F:9B:CA:7E:38",idx++); //CM201-2
        orderdMacMap.put("70:48:0F:52:ED:C1",idx++); //ali6s
        orderdMacMap.put("DC:A6:32:23:35:D4",idx++); //raspberrypi
        orderdMacMap.put("48:3C:0C:74:9B:F0",idx++); //hlold
        orderdMacMap.put("C2:31:3A:DD:51:35",idx++); //hlnew50
        orderdMacMap.put("38:F9:D3:2E:B6:DF",idx++); //alioo15
        orderdMacMap.put("A4:83:E7:3C:3F:3D",idx++); //E097815-BJ

    }


}
