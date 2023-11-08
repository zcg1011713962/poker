package org.cloud.poker.handler;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.cloud.entity.exception.BaseException;
import org.cloud.entity.poker.BasePacket;

public class BasePacketDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf in = null;
            try {
                in = ((BinaryWebSocketFrame)msg).content();
                if (in.readableBytes() < 5) {
                    throw BaseException.show("长度不足5");
                }
                // 一字节--填充
                if (in.readByte() != 124) {
                    throw BaseException.show("非约定格式");
                }
                // 两字节--包长
                short length = in.readShort();
                if (length < 0 || length > 20480) {
                    throw BaseException.show("包长大于20480或者小于0");
                }
                // 两字节--指令
                short cmd = in.readShort();
                if (in.readableBytes() < length) {
                    throw BaseException.show("非约定格式");
                }
                // 数据
                byte[] bytes = new byte[length];
                in.readBytes(bytes);
                ctx.fireChannelRead(new BasePacket(cmd, bytes));
            } finally {
                if (in != null) {
                    // 释放buf
                    in.release();
                }
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
