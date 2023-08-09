package io.github.franzli347.darach.service.impl;

import io.github.franzli347.constant.ErrorCode;
import io.github.franzli347.darach.config.minio.MinioProperties;
import io.github.franzli347.darach.model.dto.CoverTaskCreateDto;
import io.github.franzli347.darach.model.entity.VideoPath;
import io.github.franzli347.darach.repository.VideoPathRepository;
import io.github.franzli347.darach.service.TaskService;
import io.github.franzli347.darach.service.VideoPathService;
import io.github.franzli347.exception.BusinessException;
import io.github.franzli347.model.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author franz
 */
@Service
@Slf4j
public class VideoPathServiceImpl implements VideoPathService {

    VideoPathRepository videoPathRepository;

    MinioProperties minioProperties;

    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    TaskService taskService;


    public VideoPathServiceImpl(VideoPathRepository videoPathRepository,
                                MinioProperties minioProperties,
                                @Qualifier("taskExecutor") ThreadPoolTaskExecutor threadPoolTaskExecutor,
                                TaskService taskService) {
        this.videoPathRepository = videoPathRepository;
        this.minioProperties = minioProperties;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.taskService = taskService;
    }

    @Override
    public Boolean saveVideoPath(VideoPath videoPath) {
        videoPathRepository.save(videoPath);
        return true;
    }

    @Override
    public Boolean updateVideoPath(VideoPath videoPath) {
        return saveVideoPath(videoPath);
    }

    @Override
    public Boolean createVideoCoverTask(CoverTaskCreateDto coverTaskCreateDto) {
        Task task = new Task();
        task.setStatus(0);
        task.setBucketName(minioProperties.getVideoBucketName());
        task.setFileName(coverTaskCreateDto.getFileName());
        task.setTaskParam(coverTaskCreateDto.getTaskParam());
        threadPoolTaskExecutor.execute(() -> taskService.triggerCoverTask(task));
        return true;
    }


    @Override
    public List<Integer> getEpoNumsByAnimateId(Integer animateId) {
        return videoPathRepository
                .getAllByAnimateId(animateId)
                .stream()
                .map(VideoPath::getEpoNum)
                .distinct()
                .sorted()
                .toList();
    }

    @Override
    public String getVideoPathByAnimateIdAndEpoNum(Integer animateId, Integer epoNum) {
        return Optional
                .ofNullable(videoPathRepository.getAllByAnimateIdAndEpoNum(animateId, epoNum))
                .orElseThrow(() -> new BusinessException(ErrorCode.SYSTEM_ERROR))
                .getUrl();
    }


    @Override
    public VideoPath getVideoPathById(Integer id) {
        return videoPathRepository.findById(id).orElse(null);
    }

}




