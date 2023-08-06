package io.github.franzli347.darach.model.entity;

import lombok.Data;

@Data
public class JobParam {

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String fileName;
    private Integer taskId;
}
