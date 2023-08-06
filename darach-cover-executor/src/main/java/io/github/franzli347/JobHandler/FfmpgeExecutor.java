package io.github.franzli347.JobHandler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import io.github.franzli347.entity.Task;
import io.github.franzli347.utils.ResponseTaskUtil;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FfmpgeExecutor extends IJobHandler {

    private MinioClient minioClient;

    private static final String tmpPath = "/tmp/minio/";

    private static final String logFormat = "=====================  %s =====================";


    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        XxlJobHelper.log(logFormat.formatted("start cover task"));

        String param = XxlJobHelper.getJobParam();

        JobParam jobParam = JSONUtil.toBean(param, JobParam.class);
        XxlJobHelper.log(param);

        // 上报任务开始
        Task task = new Task();
        task.setId(jobParam.getTaskId());
        task.setStatus(1);
        task.setStartTime(LocalDateTime.now());
        ResponseTaskUtil.responseTask(task);

        String fileName = jobParam.getFileName();
        String filePath = tmpPath + fileName;

        if (!FileUtil.exist(tmpPath)) {
            FileUtil.mkdir(tmpPath);
        }

        if (FileUtil.exist(filePath)) {
            FileUtil.del(filePath);
        }

        minioClient = MinioClient.builder().endpoint(jobParam.getEndpoint()).credentials(jobParam.getAccessKey(), jobParam.getSecretKey()).build();

        XxlJobHelper.log(logFormat.formatted("try pull resource from oss"));

        try {
            DownloadObjectArgs downloadObjectArgs = DownloadObjectArgs.builder().bucket(jobParam.getBucketName()).object(jobParam.getFileName()).filename(filePath).build();
            minioClient.downloadObject(downloadObjectArgs);
        } catch (Exception e) {
            XxlJobHelper.log(logFormat.formatted("pull resource from oss failed"));
            task.setStatus(2);
            ResponseTaskUtil.responseTask(task);
            throw new RuntimeException(e);
        }

        // 执行ffmpeg命令
        XxlJobHelper.log(logFormat.formatted("try execute ffmpeg"));

        String fileNameWithoutExt = fileName.split("\\.")[0];


        ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c", """
ffmpeg -threads 4 -re -fflags +genpts -i "%s" -s:0 1920x1080 -ac 2 -vcodec libx264 -profile:v main -b:v:0 2000k -maxrate:0 2000k -bufsize:0 4000k -r 30 -ar 44100 -g 48 -c:a aac -b:a:0 128k -s:2 1280x720 -ac 2 -vcodec libx264 -profile:v main -b:v:1 1000k -maxrate:2 1000k -bufsize:2 2000k -r 30 -ar 44100 -g 48 -c:a aac -b:a:1 128k -s:4 720x480 -ac 2 -vcodec libx264 -profile:v main -b:v:2 600k -maxrate:4 600k -bufsize:4 1000k -r 30 -ar 44100 -g 48 -c:a aac -b:a:2 128k -map 0:v -map 0:a -map 0:v -map 0:a -map 0:v -map 0:a -f hls -var_stream_map "v:0,a:0 v:1,a:1 v:2,a:2" -hls_segment_type mpegts -start_number 10 -hls_time 10 -hls_list_size 0 -hls_start_number_source 1 -master_pl_name "%s.m3u8" -hls_segment_filename "%s_%%v-%%09d.ts" "%s_%%v.m3u8"
                """.formatted(filePath, fileNameWithoutExt , tmpPath + fileNameWithoutExt, tmpPath + fileNameWithoutExt));

        // 将标准输出和错误输出合并
        builder.redirectErrorStream(true);

        // 启动进程
        Process process = builder.start();

        // 获取输入流
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        // 逐行读取输出
        while ((line = reader.readLine()) != null) {
            XxlJobHelper.log(line);
            System.out.println(line);
        }

        // 等待进程执行完毕
        int exitCode = process.waitFor();
        XxlJobHelper.log(logFormat.formatted("脚本执行完毕，退出码：" + exitCode));


        // 删除原始视频文件
        FileUtil.del(filePath);

        // 获取临时文件夹所有文件
        File[] files = FileUtil.ls(tmpPath);

        List<File> collect = Arrays.stream(files).filter(file -> file.getName().startsWith(fileNameWithoutExt)).toList();

        // 上传文件到oss
        for (File file : collect) {
            XxlJobHelper.log(logFormat.formatted("try upload " + file.getName() + " to oss"));
            try {
                minioClient.uploadObject(io.minio.UploadObjectArgs.builder().bucket(jobParam.getBucketName()).object(file.getName()).filename(file.getAbsolutePath()).build());
            } catch (Exception e) {
                XxlJobHelper.log(logFormat.formatted("upload file to oss failed"));
                throw new RuntimeException(e);
            }
        }

        for (File file : collect) {
            FileUtil.del(file);
        }

        // 删除oss中原始视频文件
        XxlJobHelper.log(logFormat.formatted("try delete " + fileName + " from oss"));
        try {
            minioClient.removeObject(io.minio.RemoveObjectArgs.builder().bucket(jobParam.getBucketName()).object(fileName).build());
        } catch (Exception e) {
            XxlJobHelper.log(logFormat.formatted("delete file from oss failed"));
            throw new RuntimeException(e);
        }

        task.setTaskResult(Map.of(
                "masterM3u8",jobParam.getEndpoint() + "/" + jobParam.getBucketName() + "/" + fileNameWithoutExt + ".m3u8"
        ));
        task.setStatus(3);
        ResponseTaskUtil.responseTask(task);
    }

    static class JobParam {

        private String endpoint;

        private String accessKey;

        private String secretKey;

        private String bucketName;

        private String fileName;

        private Integer taskId;


        public JobParam() {
        }

        public Integer getTaskId() {
            return taskId;
        }

        public void setTaskId(Integer taskId) {
            this.taskId = taskId;
        }

        public JobParam(String endpoint, String accessKey, String secretKey, String bucketName, String fileName, Integer taskId) {
            this.endpoint = endpoint;
            this.accessKey = accessKey;
            this.secretKey = secretKey;
            this.bucketName = bucketName;
            this.fileName = fileName;
            this.taskId = taskId;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }
    }


}
