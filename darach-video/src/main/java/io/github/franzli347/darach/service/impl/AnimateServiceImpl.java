package io.github.franzli347.darach.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.darach.mapper.AnimateMapper;
import io.github.franzli347.darach.model.dto.AnimateCreateDto;
import io.github.franzli347.darach.model.dto.EpisodeDto;
import io.github.franzli347.darach.model.entity.Animate;
import io.github.franzli347.darach.model.entity.VedioPath;
import io.github.franzli347.darach.service.AnimateService;
import io.github.franzli347.darach.service.VedioPathService;
import io.github.franzli347.darach.utils.XxlJobTrigger;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Franz
 * @description 针对表【animate】的数据库操作Service实现
 * @createDate 2023-06-17 07:47:28
 */
@Service
public class AnimateServiceImpl extends ServiceImpl<AnimateMapper, Animate>
        implements AnimateService {

    @Resource
    VedioPathService vedioPathService;

    @Resource
    XxlJobTrigger xxlJobTrigger;

    @Override
    public Boolean addNewAnimate(AnimateCreateDto dto) {
        Animate animate = new Animate();
        BeanUtils.copyProperties(dto, animate);
        save(animate);
        List<EpisodeDto> episodeList = Optional.ofNullable(dto.getEpisodeList()).orElse(new ArrayList<>());
        List<VedioPath> insertData = new ArrayList<>();
        for (EpisodeDto episodeDto : episodeList) {
            VedioPath vedioPath = new VedioPath();
            vedioPath.setAnimateId(animate.getId());
            vedioPath.setFileName(episodeDto.getFileName());
            xxlJobTrigger.triggerJob(episodeDto.getFileName());
            vedioPath.setEpisode(String.valueOf(episodeDto.getEpisode()));
            insertData.add(vedioPath);
        }
        vedioPathService.saveBatch(insertData);
        return true;
    }
}