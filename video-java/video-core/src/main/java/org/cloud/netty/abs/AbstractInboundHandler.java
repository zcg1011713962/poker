package org.cloud.netty.abs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractInboundHandler<T> extends SimpleChannelInboundHandler<T> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("已连接 from {} to {}", ctx.channel().localAddress(), ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive()) {
            log.info("客户端断开连接 from {} to {}", ctx.channel().localAddress(), ctx.channel().remoteAddress());
        } else {
            log.info("服务端断开连接 from {} to {}", ctx.channel().remoteAddress(), ctx.channel().localAddress());
        }
    }

}
