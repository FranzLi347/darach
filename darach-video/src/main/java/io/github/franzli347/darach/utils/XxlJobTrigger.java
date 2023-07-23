package io.github.franzli347.darach.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import io.github.franzli347.constant.ErrorCode;
import io.github.franzli347.darach.config.minio.MinioProperties;
import io.github.franzli347.exception.XxlJobException;
import io.github.franzli347.darach.model.entity.JobParam;

import java.util.HashMap;
import java.util.Map;

public class XxlJobTrigger{
    private String address;
    private Integer jobId;
    private final MinioProperties minioProperties;

    public XxlJobTrigger(String address, Integer jobId, MinioProperties minioProperties) {
        this.address = address;
        this.jobId = jobId;
        this.minioProperties = minioProperties;
    }

    private static final String TRIGGER_URL = "jobinfo/trigger";
    public String triggerJob(String fileName){
        address = addSplit4url(address);
        String triggerUrl = address + TRIGGER_URL;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", jobId);
        JobParam jobParam = new JobParam();
        jobParam.setEndpoint(minioProperties.getEndpoint());
        jobParam.setAccessKey(minioProperties.getAccessKey());
        jobParam.setSecretKey(minioProperties.getSecretKey());
        jobParam.setBucketName(minioProperties.getBucketName());
        jobParam.setFileName(fileName);
        paramMap.put("executorParam", JSONUtil.toJsonStr(jobParam));
        paramMap.put("addressList", "");
        try{
            return HttpUtil.post(triggerUrl, paramMap, 10000);
        }catch (Exception e){
            throw new XxlJobException(ErrorCode.SYSTEM_ERROR.getCode(),"触发任务失败");
        }
    }

    private static String addSplit4url(String url) {
        if (url.endsWith("/")) {
            return url;
        } else {
            return url + "/";
        }
    }

}
