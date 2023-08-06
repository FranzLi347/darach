package io.github.franzli347.darach.service.impl;

import io.github.franzli347.Repository.TaskRepository;
import io.github.franzli347.darach.config.minio.MinioProperties;
import io.github.franzli347.darach.model.dto.CoverTaskCreateDto;
import io.github.franzli347.darach.model.entity.VideoPath;
import io.github.franzli347.darach.repository.VideoPathRepository;
import io.github.franzli347.darach.service.VideoPathService;
import io.github.franzli347.darach.utils.XxlJobTrigger;
import io.github.franzli347.model.entity.Task;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author franz
 */
@Service
public class VideoPathServiceImpl implements VideoPathService {

    @Resource
    VideoPathRepository videoPathRepository;

    @Resource
    MinioClient minioClient;

    @Resource
    MinioProperties minioProperties;

    @Resource
    TaskRepository taskRepository;

    @Resource
    XxlJobTrigger xxlJobTrigger;

    @Resource
    @Qualifier("taskExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static final Integer MAX_RETRY_TYPE = 30;

    private static final Integer WAIT_TIME = 100000;

    @Override
    public List<VideoPath> getVideoPathByAnimateId(Integer animateId) {
        return videoPathRepository.getAllByAnimateId(animateId);
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
        taskRepository.save(task);
        threadPoolTaskExecutor.execute(() -> triggerCoverTask(task));
        return true;
    }

    @Override
    public Boolean responseTask(Integer id, Task task) {
        task.setId(id);
        taskRepository.save(task);
        return true;
    }

    @SneakyThrows
    @Async("taskExecutor")
    protected void triggerCoverTask(Task task) {
        boolean objectExist = isObjectExist(task.getBucketName(), task.getFileName());
        int retryTime = 0;
        while (!objectExist && retryTime < MAX_RETRY_TYPE) {

            Thread.sleep(WAIT_TIME);
            retryTime++;
            objectExist = isObjectExist(task.getBucketName(), task.getFileName());
        }
        if (objectExist) {
            System.out.println("trigger job success");
            task.setStatus(1);
            taskRepository.save(task);
            xxlJobTrigger.triggerJob(task);
            return;
        }
        task.setStatus(2);
        taskRepository.save(task);
    }

    public boolean isObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @Override
    public VideoPath getVideoPathById(Integer id) {
        return videoPathRepository.findById(id).orElse(null);
    }
}




