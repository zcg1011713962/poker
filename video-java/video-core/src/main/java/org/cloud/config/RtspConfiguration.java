package org.cloud.config;

import org.cloud.emu.Protocol;
import org.cloud.manager.CacheManager;
import org.cloud.properties.RtspProperties;
import org.cloud.rtsp.init.RtpServerlInitializer;
import org.cloud.rtsp.init.RtspClientlInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "rtsp", name = "enable", havingValue = "true")
public class RtspConfiguration {

    @Bean
    public RtspClientlInitializer rtspClientlInitializer(RtspProperties rtspProperties){
        RtspClientlInitializer handler = new RtspClientlInitializer(rtspProperties.isProxy());
        CacheManager.protocolTable().put(Protocol.RTSP, handler, false);
        return handler;
    }

    @Bean
    public RtpServerlInitializer rtpServerlInitializer(RtspProperties rtspProperties){
        RtpServerlInitializer handler = new RtpServerlInitializer(rtspProperties.isProxy());
        CacheManager.protocolTable().put(Protocol.RTP, handler, false);
        return handler;
    }


}
