package org.cloud.rtp.config;


import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.cloud.emu.Protocol;
import org.cloud.entity.properties.RtpProperties;
import org.cloud.manager.CacheManager;
import org.cloud.netty.abs.AbstractInitializer;
import org.cloud.rtp.handler.RtpResponseHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "rtp", name = "enable", havingValue = "true")
public class RtpConfiguration {

    @Bean
    public AbstractInitializer rtpServerlInitializer(RtpProperties rtpProperties){
        AbstractInitializer handler = new AbstractInitializer<NioDatagramChannel>(rtpProperties.isProxy()) {
            @Override
            protected void initChannel(NioDatagramChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("rtpResponseHandler", new RtpResponseHandler()); // Inbound
            }
        };
        CacheManager.protocolTable().put(Protocol.RTP, handler, false);
        return handler;
    }


}
