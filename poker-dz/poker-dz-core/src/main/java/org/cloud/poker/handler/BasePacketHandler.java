package org.cloud.poker.handler;


import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.cloud.netty.abs.AbstractInboundHandler;
import org.springframework.web.reactive.DispatcherHandler;

@Slf4j
public class BasePacketHandler extends AbstractInboundHandler<BasePacketHandler> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BasePacketHandler basePacketHandler) throws Exception {
        log.debug("收到游戏数据包请求");
        /* // 根据cmd 执行不同的handler
        DispatcherHandler.getHandler(packet.getCmd()).exec(packet);*/
    }
}
