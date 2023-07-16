package org.cloud.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "websocket")
@Data
public class WebSocketProperties {
    private boolean enable;
    private boolean proxy;

}
