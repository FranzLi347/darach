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
                        .bucket(minioProperties.getCoverBucketName())
                        .object(name)
                        .method(Method.PUT)
                        .expiry(60 * 60 * 2)
                        .build());
    }

    @Override
    @SneakyThrows
    public String getTempVideoUrl(String name) {
        if (StrUtil.isBlankIfStr(name)) {
            name = UUID.fastUUID().toString();
        }
        return minioClient
                .getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        .bucket(minioProperties.getVideoBucketName())
                        .object(name)
                        .method(Method.PUT)
                        .expiry(60 * 60 * 2)
                        .build());
    }

    @Override
    public String getOssUrl(String type) {
        switch (type) {
            case "cover" -> {
                return minioProperties.getEndpoint() + "/" + minioProperties.getCoverBucketName();
            }
            case "video" -> {
               return minioProperties.getEndpoint() + "/" + minioProperties.getVideoBucketName();
            }
            default -> {
                return null;
            }
        }
    }
}




