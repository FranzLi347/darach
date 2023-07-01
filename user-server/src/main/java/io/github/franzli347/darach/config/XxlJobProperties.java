package io.github.franzli347.darach.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "xxl-job")
public class XxlJobProperties {

    private String address;

    private Integer jobId;

}
