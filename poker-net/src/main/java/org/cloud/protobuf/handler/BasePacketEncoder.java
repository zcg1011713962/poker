package org.cloud.protobuf.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.cloud.entity.poker.BasePacket;

public class BasePacketEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof BasePacket) {
            BasePacket packet = (BasePacket)msg;
            ByteBuf buf = Unpooled.buffer(5 + packet.getBytes().length);
            // 填充数据
            buf.writeByte(124);
            // 包长
            buf.writeShort(packet.getBytes().length);
            // 协议码
            buf.writeShort(packet.getProtoCode());
            // 数据
            buf.writeBytes(packet.getBytes());
            BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buf);
            ctx.writeAndFlush(frame);
        } else {
            ctx.writeAndFlush(msg);
        }
    }
}
