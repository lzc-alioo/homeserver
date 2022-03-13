package com.alioo.monitor.homeserver;

import com.alioo.monitor.tv.UDPClient;
import org.junit.jupiter.api.Test;

public class UDPClientTest {

    @Test
    public void sendtest() throws InterruptedException {

//        UDPClient.send("192.168.16.214",9900,"{\"CONTROL_ACTION\":\"return\"}");
//        Thread.sleep(500L);
//        UDPClient.send("192.168.16.214",9900,"{\"CONTROL_ACTION\":\"return\"}");
//        Thread.sleep(500L);
//        UDPClient.send("192.168.16.214",9900,"{\"CONTROL_ACTION\":\"ok\"}");

        UDPClient.send("192.168.16.214",9900,"{\"CONTROL_ACTION\":\"power\"}");


    }
}
