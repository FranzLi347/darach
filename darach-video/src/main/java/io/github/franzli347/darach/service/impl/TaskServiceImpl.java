package io.github.franzli347.darach.service.impl;

import io.github.franzli347.Repository.TaskRepository;
import io.github.franzli347.darach.service.TaskService;
import io.github.franzli347.darach.utils.XxlJobTrigger;
import io.github.franzli347.model.entity.Task;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private static final Integer MAX_RETRY_TYPE = 30;

    private static final Integer WAIT_TIME = 100000;


    MinioClient minioClient;

    TaskRepository taskRepository;


    XxlJobTrigger xxlJobTrigger;


    public TaskServiceImpl(MinioClient minioClient, TaskRepository taskRepository, XxlJobTrigger xxlJobTrigger) {
        this.minioClient = minioClient;
        this.taskRepository = taskRepository;
        this.xxlJobTrigger = xxlJobTrigger;
    }


    @Override
    @SneakyThrows
    public void triggerCoverTask(Task task) {
        taskRepository.save(task);
        boolean objectExist = isObjectExist(task.getBucketName(), task.getFileName());
        int retryTime = 0;
        while (!objectExist && retryTime < MAX_RETRY_TYPE) {
            Thread.sleep(WAIT_TIME);
            retryTime++;
            objectExist = isObjectExist(task.getBucketName(), task.getFileName());
            log.debug("thread {} retry time: {}", Thread.currentThread().getName(),retryTime);
        }
        if (objectExist) {
            log.debug("trigger job success");
            task.setStatus(1);
            taskRepository.save(task);
            xxlJobTrigger.triggerJob(task);
            return;
        }
        task.setStatus(2);
        taskRepository.save(task);
    }

    @Override
    public Boolean responseTask(Integer id, Task task) {
        task.setId(id);
        taskRepository.save(task);
        return true;
    }

    private boolean isObjectExist(String bucketName, String objectName) {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }
}
