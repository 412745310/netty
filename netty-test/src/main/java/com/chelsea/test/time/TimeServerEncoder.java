package com.chelsea.test.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

import com.chelsea.test.domain.MyObj;

public class TimeServerEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        MyObj m = (MyObj) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeCharSequence(m.getValue(), CharsetUtil.UTF_8);
        ctx.write(encoded, promise); // (1)
    }

}
