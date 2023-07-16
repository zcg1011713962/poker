package org.cloud.config;

import org.cloud.emu.Protocol;
import org.cloud.manager.CacheManager;
import org.cloud.properties.WebSocketProperties;
import org.cloud.websocket.init.WebSocketInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "websocket", name = "enable", havingValue = "true")
public class WebSocketConfiguration {

    @Bean
    public WebSocketInitializer websocketInitializer(WebSocketProperties websocketProperties){
        WebSocketInitializer handler = new WebSocketInitializer(websocketProperties.isProxy());
        CacheManager.protocolTable().put(Protocol.WEBSOCKET, handler, false);
        return handler;
    }

}
