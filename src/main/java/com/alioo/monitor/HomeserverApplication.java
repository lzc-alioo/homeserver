package com.alioo.monitor;

import com.alioo.monitor.homeserver.NettyServer;
import com.alioo.monitor.homeserver.ServerHandler;
import com.alioo.monitor.router.FlowStatisticService;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;
import java.util.Random;

@SpringBootApplication
public class HomeserverApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(HomeserverApplication.class);

    @Value("${netty.port}")
    private int port;

    @Value("${netty.url}")
    private String url;

    @Autowired
    private NettyServer server;

    @Autowired
    private FlowStatisticService flowStatisticService;


    public static void main(String[] args) {
        SpringApplication.run(HomeserverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {

            while (true) {
                logger.info("ServerHandler.set=" + ServerHandler.set);

                String ret = "aabbcc" + new Random().nextInt(100) + "\n";


                for (ChannelHandlerContext ctx : ServerHandler.set) {

                    ctx.write(ret);
                    ctx.flush();
                }

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }).start();

        flowStatisticService.getList();

        InetSocketAddress address = new InetSocketAddress(url, port);
        System.out.println("run  .... . ... " + url);
        server.start(address);


    }

}
