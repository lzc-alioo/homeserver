package com.alioo.monitor.homeserver;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    public static Set<ChannelHandlerContext> set = new HashSet<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive----->");

        set.add(ctx);


    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(ctx.channel().remoteAddress() + "----->Server receive:" + msg.toString());

        if (msg.toString().equals("exit")) {
            ChannelFuture future = ctx.writeAndFlush("will exit");

            logger.info(ctx.channel().remoteAddress() + "----->Client exit");
//            future.addListener(ChannelFutureListener.CLOSE).sync().channel().closeFuture().sync();
            future.addListener(ChannelFutureListener.CLOSE);

        } else {

            String ret = "server say :" + msg;

            //将客户端的信息直接返回写入ctx
            ctx.write(ret);
            //刷新缓存区
            ctx.flush();

            logger.info(ctx.channel().remoteAddress() + "----->Server send:" + ret);

        }


    }


//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelRegistered...");
//        ctx.fireChannelRegistered();
//    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        //执行客户端断开连接后的业务操作
        logger.info("handlerRemoved ctx=" + ctx);
        set.remove(ctx);


    }

}

