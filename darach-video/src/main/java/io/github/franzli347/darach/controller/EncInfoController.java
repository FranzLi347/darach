package io.github.franzli347.darach.controller;

import cn.hutool.crypto.asymmetric.RSA;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EncInfoController {

    @Resource
    RSA rsa;

    @Resource
    RedisTemplate redisTemplate;

    @GetMapping("/pbk")
    public String getPublicKey(){
        return rsa.getPublicKeyBase64();
    }

}
