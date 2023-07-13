package io.github.franzli347.darach.config.xxlJob;

import io.github.franzli347.darach.config.minio.MinioProperties;
import io.github.franzli347.darach.utils.XxlJobTrigger;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Franz
 * xxl-job配置
 */
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
