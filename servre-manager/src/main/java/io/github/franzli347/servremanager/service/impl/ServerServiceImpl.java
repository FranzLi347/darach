package io.github.franzli347.servremanager.service.impl;

import io.github.franzli347.servremanager.service.ServerService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class ServerServiceImpl implements ServerService {

    @Resource
    RedisTemplate redisTemplate;

    @Scheduled(fixedRate = 5000)
    public void checkServerStatus(){
        Set server = redisTemplate.opsForSet().members("server");
//        log.info("server: {}", server);
        server.forEach(ipAndStatus -> {
            String[] split = ipAndStatus.toString().split(":");
            String ip = split[0];
            String status = split[1];
            if (status.equals("live") && !redisTemplate.hasKey("server:" + ip)){
                log.error("server: {} is dead", ip);
                redisTemplate.opsForSet().add("server", ip + ":" + "dead");
                redisTemplate.opsForSet().remove("server", ip + ":" + "live");
            }
        });

    }

}
