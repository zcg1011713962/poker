package org.cloud.protobuf.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StateHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                // 读空闲处理逻辑
                log.info("读空闲处理");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                // 写空闲处理逻辑
                log.info("写空闲处理逻辑");
            } else if (event.state() == IdleState.ALL_IDLE) {
                // 读写都空闲处理逻辑
                log.info("读写都空闲处理");
            }
        }
    }
}
