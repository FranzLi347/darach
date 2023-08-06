package io.github.franzli347.entity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Task {
    private Integer id;

    private String fileName;

    private String bucketName;

    private Integer status;

    private Map<String, Object> taskParam;

    private Map<String, Object> taskResult;

    private LocalDateTime startTime;

    private LocalDateTime insertTime;

    public Task() {
    }

    public Task(Integer id, String fileName, String bucketName, Integer status, Map<String, Object> taskParam, Map<String, Object> taskResult, LocalDateTime startTime, LocalDateTime insertTime) {
        this.id = id;
        this.fileName = fileName;
        this.bucketName = bucketName;
        this.status = status;
        this.taskParam = taskParam;
        this.taskResult = taskResult;
        this.startTime = startTime;
        this.insertTime = insertTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, Object> getTaskParam() {
        return taskParam;
    }

    public void setTaskParam(Map<String, Object> taskParam) {
        this.taskParam = taskParam;
    }

    public Map<String, Object> getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(Map<String, Object> taskResult) {
        this.taskResult = taskResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(fileName, task.fileName) && Objects.equals(bucketName, task.bucketName) && Objects.equals(status, task.status) && Objects.equals(taskParam, task.taskParam) && Objects.equals(taskResult, task.taskResult) && Objects.equals(startTime, task.startTime) && Objects.equals(insertTime, task.insertTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, bucketName, status, taskParam, taskResult, startTime, insertTime);
    }
}