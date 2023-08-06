package io.github.franzli347.config.webmvc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Franz
 * 跨域配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "cors-config")
public class CorsProperties {

        private Boolean enabled;

        private Boolean allowCredentials;

        private String allowedOrigin;

        private String allowedMethods;

        private String allowedHeaders;

        private String exposedHeaders;

        private String pathPattern;

        private Long maxAge;

}
