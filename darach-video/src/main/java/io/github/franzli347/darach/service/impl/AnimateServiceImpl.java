package io.github.franzli347.darach.service.impl;

import io.github.franzli347.darach.constant.VideoRedisConstant;
import io.github.franzli347.darach.model.dto.AnimateDto;
import io.github.franzli347.darach.model.dto.HomePageVideoData;
import io.github.franzli347.darach.model.entity.Animate;
import io.github.franzli347.darach.repository.AnimateRepository;
import io.github.franzli347.darach.repository.VideoPathRepository;
import io.github.franzli347.darach.service.AnimateService;
import io.github.franzli347.exception.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Franz
 * @description 针对表【animate】的数据库操作Service实现
 * @createDate 2023-06-17 07:47:28
 */
@Service
public class AnimateServiceImpl implements AnimateService {

    @Resource
    AnimateRepository animateRepository;

    @Resource
    VideoPathRepository videoPathRepository;

    @Resource
    RedisTemplate<String,HomePageVideoData> redisTemplate;

    @Override
    public Boolean addNewAnimate(AnimateDto dto) {
        Animate animate = new Animate();
        BeanUtils.copyProperties(dto, animate);
        animateRepository.save(animate);
        return true;
    }

    @Override
    public Boolean updateAnimate(AnimateDto dto, Integer id) {
        Animate animate = new Animate();
        BeanUtils.copyProperties(dto, animate);
        animate.setId(id);
        animateRepository.save(animate);
        return true;
    }

    @Override
    public Boolean deleteAnimate(Integer id) {
        if (!videoPathRepository.getAllByAnimateId(id).isEmpty()) {
            throw new BusinessException(50000,"该动画下存在视频，无法删除");
        }
        animateRepository.deleteById(id);
        return true;
    }

    @Override
    public HomePageVideoData getHomePageVideoData() {
        return redisTemplate.opsForValue().get(VideoRedisConstant.HOME_PAGE_VIDEO_DATA_KEY);
    }
}
