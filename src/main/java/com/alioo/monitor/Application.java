package com.alioo.monitor;

import com.alioo.monitor.homeserver.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Value("${netty.ip}")
    private String ip;

    @Value("${netty.port}")
    private int port;

    @Autowired
    private NettyServer nettyServer;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        new Thread(() -> {
//
//            while (true) {
//                logger.info("ServerHandler.set=" + ServerHandler.set);
//
//                String ret = "aabbcc" + new Random().nextInt(100) + "\n";
//
//
//                for (ChannelHandlerContext ctx : ServerHandler.set) {
//
//                    ctx.write(ret);
//                    ctx.flush();
//                }
//
//                try {
//                    Thread.sleep(60000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//        }).start();

//        flowStatisticService.getList();

        InetSocketAddress address = new InetSocketAddress(ip, port);
        log.info("NettyServer启动成功.... . ... " + ip + ":" + port);
        nettyServer.start(address);

    }

}
