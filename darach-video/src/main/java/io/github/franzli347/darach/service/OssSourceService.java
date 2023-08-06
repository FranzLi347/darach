package io.github.franzli347.darach.service;

public interface OssSourceService {

    String getTempUrl(String name);

    String getTempVideoUrl(String name);


    String getOssUrl(String type);
}
