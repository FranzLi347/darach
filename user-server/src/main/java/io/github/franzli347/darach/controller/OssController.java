package io.github.franzli347.darach.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import io.github.franzli347.darach.common.ResponseResult;
import io.github.franzli347.darach.service.OssSourceService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/oss")
@RestController
public class OssController {

    @Resource
    private OssSourceService service;

    @Resource
    private MinioClient minioClient;

    @GetMapping("tmpUrl/{name}")
    public ResponseEntity<ResponseResult> getTempUrl(@PathVariable String name) {
        if (StrUtil.isBlankIfStr(name)) {
            name = UUID.fastUUID().toString();
        }
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs
                    .builder()
                    .bucket("animate")
                    .object(name)
                    .method(Method.PUT)
                    .expiry(60 * 60 * 24)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> m = Map.of("url", url);
        return ResponseEntity.ok(ResponseResult.success(m));
    }

}

