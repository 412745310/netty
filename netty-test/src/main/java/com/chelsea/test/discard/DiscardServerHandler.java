package com.chelsea.test.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端处理类
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        // try {
        // while (in.isReadable()) {
        // System.out.print((char) in.readByte());
        // System.out.flush();
        // }
        // } finally {
        // ReferenceCountUtil.release(msg);
        // }

        System.out.print(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        // in.release();

        // 消息写入缓冲区
        ctx.write(msg);
        // 消息发回客户端
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
