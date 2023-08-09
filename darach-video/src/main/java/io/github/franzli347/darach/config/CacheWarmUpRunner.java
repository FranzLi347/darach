package io.github.franzli347.darach.config;

import io.github.franzli347.darach.constant.VideoRedisConstant;
import io.github.franzli347.darach.model.dto.HomePageVideoData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheWarmUpRunner implements CommandLineRunner {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    private void warmRedis(){
        HomePageVideoData homePageVideoData = new HomePageVideoData();
        homePageVideoData.setDisplayName("義妹生活");
        homePageVideoData.setVideoUrl("http://m.741588.xyz:9000/video/em.mp4");
        homePageVideoData.setTargetRedirectUrl("http://m.741588.xyz:9000/video/em.mp4");
        redisTemplate.opsForValue().set(VideoRedisConstant.HOME_PAGE_VIDEO_DATA_KEY, homePageVideoData);
        log.info("HOME_PAGE_VIDEO_DATA  warmed up");
    }

    @Override
    public void run(String... args) throws Exception {
        warmRedis();
    }


}
