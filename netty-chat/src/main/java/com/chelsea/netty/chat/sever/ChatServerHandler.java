package com.chelsea.netty.chat.sever;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 服务器主要的业务逻辑
 * 
 * @author shevchenko
 *
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 保存所有活动的用户
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 1. 建立链接时执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            ch.writeAndFlush("[" + channel.remoteAddress() + "] " + "is comming");
        }
        group.add(channel);
    }

    // 2. 建立链接时执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] " + "online");
        ctx.writeAndFlush("[server]: welcome");
    }

    // 3. 接收客户端消息
    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) throws Exception {
        Channel channel = arg0.channel();
        // 当有用户发送消息的时候，对其他用户发送信息
        for (Channel ch : group) {
            if (ch == channel) {
                ch.writeAndFlush("[you]：" + arg1 + "\n");
            } else {
                ch.writeAndFlush("[" + channel.remoteAddress() + "]: " + arg1 + "\n");
            }
        }
        System.out.println("[" + channel.remoteAddress() + "]: " + arg1 + "\n");
    }

    // 4. 异常时执行
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("[" + ctx.channel().remoteAddress() + "]" + "exit the room");
        ctx.close().sync();
    }

    // 5. 退出链接时执行
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] " + "offline");
    }

    // 6. 退出链接时执行
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            ch.writeAndFlush("[" + channel.remoteAddress() + "] " + "exit the room");
        }
        group.remove(channel);
    }

}
