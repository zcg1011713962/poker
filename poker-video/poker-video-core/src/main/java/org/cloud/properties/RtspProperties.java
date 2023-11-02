package org.cloud.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rtsp")
@Data
public class RtspProperties {
    private boolean enable;
    private boolean proxy;

}
