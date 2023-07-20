package org.cloud.config;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.rtsp.RtspDecoder;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.cloud.emu.Protocol;
import org.cloud.handler.rtsp.RtspResponseHandler;
import org.cloud.manager.CacheManager;
import org.cloud.netty.UserEventHandler;
import org.cloud.netty.abs.AbstractInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.cloud.properties.RtspProperties;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnProperty(prefix = "rtsp", name = "enable", havingValue = "true")
public class RtspConfiguration {

    @Bean
    public AbstractInitializer rtspClientlInitializer(RtspProperties rtspProperties){
        AbstractInitializer handler = new AbstractInitializer<SocketChannel>(rtspProperties.isProxy()) {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                if(proxy){
                    pipeline.addLast("socks4ProxyHandler", new Socks4ProxyHandler(new InetSocketAddress( "127.0.0.1",8808))); // Inbound Outbound
                }
                pipeline.addLast("rtspIdleStateHandler", new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS)); // Inbound Outbound
                pipeline.addLast("rtspHeartBeatHandler", new UserEventHandler()); // Inbound
                pipeline.addLast("rtspDecoder", new RtspDecoder()); // Inbound
                pipeline.addLast("rtspResponseHandler", new RtspResponseHandler()); // Inbound
            }
        };
        CacheManager.protocolTable().put(Protocol.RTSP, handler, false);
        return handler;
    }

}
