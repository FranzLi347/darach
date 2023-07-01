package io.github.franzli347.darach.config;

import io.github.franzli347.darach.utils.XxlJobTrigger;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MinioProperties.class,XxlJobProperties.class})
public class XxlJobConfig {

    @Resource
    XxlJobProperties xxlJobProperties;

    @Resource
    MinioProperties minioProperties;

    @Bean
    public XxlJobTrigger xxlJobTrigger(){
        return new XxlJobTrigger(xxlJobProperties.getAddress(),xxlJobProperties.getJobId(),minioProperties);
    }

}
