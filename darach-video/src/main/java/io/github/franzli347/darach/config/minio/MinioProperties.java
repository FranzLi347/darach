package io.github.franzli347.darach.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Franz
 * Minio配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;

    /**
     * 存储封面桶名称
     */
    private String coverBucketName;

    /**
     * 存储视频桶名称
     */
    private String videoBucketName;
}
