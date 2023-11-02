package org.cloud.handler.rtp;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import org.cloud.manager.ServerManager;
import org.cloud.netty.abs.AbstractInboundHandler;
import org.cloud.rtp.RtpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
public class RtpResponseHandler extends AbstractInboundHandler<DatagramPacket> {
    @Autowired(required = false)
    private ApplicationEventPublisher publisher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
        RtpServer rtpServer  = (RtpServer) ServerManager.server(ctx.channel().id().asLongText());
        log.info("upd数据包 len:{} {}", msg.content().capacity(), msg);
    }


}
