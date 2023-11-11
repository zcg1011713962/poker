package org.cloud.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rtp")
@Data
public class RtpProperties {
    private boolean enable;
    private boolean proxy;

}
