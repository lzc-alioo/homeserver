package com.alioo.monitor.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * 客户端
 */
@Slf4j
public class UDPClient {


    public static void send(String ip, int port, String command) {
        try {
            log.info("UDP消息发送ip:{},port:{},command:{}", ip, port, command);
            /*
             * 向服务器端发送数据
             */
            // 1.定义服务器的地址、端口号、数据
//        InetAddress address = InetAddress.getByName("192.168.0.13");
//        int port = 9900;
//        byte[] data = "{\"CONTROL_ACTION\":\"ok\"}".getBytes();
            InetAddress address = InetAddress.getByName(ip);
            byte[] data = command.getBytes();
            // 2.创建数据报，包含发送的数据信息
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            // 3.创建DatagramSocket对象
            DatagramSocket socket = new DatagramSocket();
            // 4.向服务器端发送数据报
            socket.send(packet);

//        /*
//         * 接收服务器端响应的数据
//         */
//        // 1.创建数据报，用于接收服务器端响应的数据
//        byte[] data2 = new byte[1024];
//        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
//        // 2.接收服务器响应的数据
//        socket.receive(packet2);
//        // 3.读取数据
//        String reply = new String(data2, 0, packet2.getLength());
//        System.out.println("我是客户端，服务器说：" + reply);
            // 4.关闭资源
            socket.close();
        } catch (IOException e) {
            log.error("发送udp包时异常", e);
        }
    }
}
