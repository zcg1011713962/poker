package org.cloud.websocket.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.cloud.netty.abs.AbstractInboundHandler;

@Slf4j
public class BinaryWebSocketFrameHandler extends AbstractInboundHandler<BinaryWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        log.info("BinaryWebSocketFrameHandler:{}", msg);
    }

}
