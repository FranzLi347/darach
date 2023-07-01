package io.github.franzli347.darach.model.vo;


import lombok.Data;

@Data
public class OssVo {

    /**
     * oss类型
     */
    private String type;

    /**
     * oss url
     */
    private String url;

    private String accessKey;

    private String secertKey;

    private String bucketName;


}
