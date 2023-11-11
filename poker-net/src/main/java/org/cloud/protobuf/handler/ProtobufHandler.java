package org.cloud.protobuf.handler;


import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.poker.BasePacket;
import org.cloud.netty.abs.AbstractInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接收BasePacket类型数据包
 */
@Slf4j
@Component
public class ProtobufHandler extends AbstractInboundHandler<BasePacket>{
    @Autowired
    private DispatcherHandler dispatcherHandler;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BasePacket packet) throws Exception {
        log.debug("收到游戏数据包请求");
        // 处理转发
        dispatcherHandler.getHandleByCode(packet.getProtoCode()).handle(packet.getBytes());
    }

}
