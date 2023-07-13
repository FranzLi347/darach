package io.github.franzli347.darach.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import io.github.franzli347.darach.config.minio.MinioProperties;
import io.github.franzli347.darach.service.OssSourceService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;

import io.minio.http.Method;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author Franz
 *
 */
@Service
public class OssSourceServiceImpl implements OssSourceService {


    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    public OssSourceServiceImpl(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @Override
    @SneakyThrows
    public String getTempUrl(String name) {
        if (StrUtil.isBlankIfStr(name)) {
            name = UUID.fastUUID().toString();
        }
        return minioClient
                .getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(name)
                .method(Method.PUT)
                .expiry(60 * 60 * 2)
                .build());
    }
}




