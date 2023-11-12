package org.cloud.protobuf.handler;


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
                // 前置约定长度3
                if (in.readableBytes() < 3) {
                    throw BaseException.show("长度不足前置约定长度3");
                }
                // 一字节--约定标志位
                if (in.readByte() != 110) {
                    throw BaseException.show("非约定标志位");
                }
                // 一字节--cmd
                byte cmd = in.readByte();
                // 一字节--包长
                byte length = in.readByte();
                if (length < 0 || length > 20480) {
                    throw BaseException.show("包长大于20480或者小于0");
                }
                // 数据包
                if (in.readableBytes() < length) {
                    throw BaseException.show("包长不足,非约定格式数据包");
                }
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
