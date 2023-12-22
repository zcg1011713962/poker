package org.cloud.protobuf.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class WebSocketHeartbeatHandler extends ChannelInboundHandlerAdapter {
    private static final String PING_MESSAGE = "ping";
    private static final String PONG_MESSAGE = "pong";
    private static final long HEARTBEAT_INTERVAL_SECONDS = 5;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("写空闲超时");
            }else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                // 读空闲超时
                log.info("Sending PING to client");
                ctx.writeAndFlush(new TextWebSocketFrame(PING_MESSAGE));
            } else if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                // 读写超时事件，发送心跳消息
                log.info("读写超时");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("已连接 from {} to {}", ctx.channel().localAddress(), ctx.channel().remoteAddress());
        // 启动定时任务，定期检查心跳
        ctx.executor().scheduleAtFixedRate(() -> {
                    if (ctx.channel().isActive()) {
                        // 通道仍然是活跃的，发送心跳消息
                        log.info("Sending schedule PING to client");
                        ctx.writeAndFlush(new TextWebSocketFrame(PING_MESSAGE));
                    }
                },
                HEARTBEAT_INTERVAL_SECONDS,
                HEARTBEAT_INTERVAL_SECONDS,
                TimeUnit.SECONDS
        );
    }

}
