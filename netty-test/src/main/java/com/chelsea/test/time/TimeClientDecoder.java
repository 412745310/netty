package com.chelsea.test.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

import com.chelsea.test.domain.MyObj;

public class TimeClientDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() > 0) {
            MyObj obj = new MyObj();
            obj.setValue(in.readCharSequence(in.readableBytes(), CharsetUtil.UTF_8).toString());
            out.add(obj);
        }
    }
}
