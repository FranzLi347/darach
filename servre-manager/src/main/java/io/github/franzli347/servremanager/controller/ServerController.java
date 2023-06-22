package io.github.franzli347.servremanager.controller;

import io.github.franzli347.servremanager.model.entity.Server;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/server")
@Slf4j
public class ServerController {

    @Resource
    RedisTemplate redisTemplate;

    @PostMapping("live")
    public ResponseEntity<String> live(@RequestBody Server server){
//        log.info("server up: {}", server);
        redisTemplate.opsForSet().remove("server", server.getIp() + ":" + "dead");
        redisTemplate.opsForSet().add("server", server.getIp() + ":" + "live");
        server.setUpdateTime(LocalDateTime.now().toString());
        redisTemplate.opsForValue().set("server:" + server.getIp(), server,5, TimeUnit.SECONDS);
        return ResponseEntity.ok("success");
    }

    @GetMapping("getLiveSever")
    public ResponseEntity getLiveSever(){
        return ResponseEntity.ok(redisTemplate.opsForSet().members("server").stream().filter(val -> {
            String v = (String) val;
            return v.contains("live");
        }).map(val -> {String v = (String) val;return v.split(":")[0];}).collect(Collectors.toSet()));
    }

    @GetMapping("getServerInfo/{ip}")
    public ResponseEntity getServerInfo(@PathVariable String ip){
        return ResponseEntity.ok(redisTemplate.opsForValue().get("server:" + ip));
    }


}
