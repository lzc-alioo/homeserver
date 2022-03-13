package com.alioo.monitor.tv;

import java.util.HashMap;
import java.util.Map;

public class LeTvControl {

    private static Map<String, String> commandMap = new HashMap<>();


    static {
        commandMap.put("volume_down", "{\"CONTROL_ACTION\":\"volume_down\"}");
        commandMap.put("volume_up", "{\"CONTROL_ACTION\":\"volume_up\"}");

        commandMap.put("channel_down", "{\"CONTROL_ACTION\":\"channel_down\"}");
        commandMap.put("channel_up", "{\"CONTROL_ACTION\":\"channel_up\"}");


        commandMap.put("up", "{\"CONTROL_ACTION\":\"up\"}");
        commandMap.put("down", "{\"CONTROL_ACTION\":\"down\"}");
        commandMap.put("left", "{\"CONTROL_ACTION\":\"left\"}");
        commandMap.put("right", "{\"CONTROL_ACTION\":\"right\"}");
        commandMap.put("ok", "{\"CONTROL_ACTION\":\"ok\"}");

        commandMap.put("return", "{\"CONTROL_ACTION\":\"return\"}");
        commandMap.put("home", "{\"CONTROL_ACTION\":\"home\"}");
        commandMap.put("menu", "{\"CONTROL_ACTION\":\"menu\"}");
        commandMap.put("setting", "{\"CONTROL_ACTION\":\"setting\"}");
        commandMap.put("power", "{\"CONTROL_ACTION\":\"power\"}");

    }

    public static void sendCommond(String ip, int port, String command){
        UDPClient.send(ip,port,commandMap.get(command));

    }


}
