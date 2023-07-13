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

    @GetMapping("/{name}")
    public String tempUrl(@PathVariable String name) {
        return service.getTempUrl(name);
    }

}

