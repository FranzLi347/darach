package io.github.franzli347.darach.controller;

import io.github.franzli347.darach.service.OssSourceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/oss")
@RestController
public class OssController {

    @Resource
    private OssSourceService service;

    @GetMapping("/{type}")
    public String ossUrl(@PathVariable String type) {
        return service.getOssUrl(type);
    }

    @GetMapping("cover/{name}")
    public String tempUrl(@PathVariable String name) {
        return service.getTempUrl(name);
    }

    @GetMapping("video/{name}")
    public String tempVideoUrl(@PathVariable String name) {
        return service.getTempVideoUrl(name);
    }

}

