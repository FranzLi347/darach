package io.github.franzli347.darach.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "capture-img")
public class CaptureImgProperties {
    private Boolean enable;
    private Integer width;
    private Integer height;
    private String type;
}
