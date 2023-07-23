package com.xxl.job.executor.sample.frameless.JobHandler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;

import java.io.*;

public class FfmpgeExecutor extends IJobHandler {

    private MinioClient minioClient;

    private static final String tmpPath = "/home/franz/tmp/cover-executor/";

    private static final String logFormat = "=====================  %s =====================";

    private static JobParam jobParam;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        XxlJobHelper.log(logFormat.formatted("start cover task"));

        String param = XxlJobHelper.getJobParam();

        jobParam = JSONUtil.toBean(param, JobParam.class);
        XxlJobHelper.log(param);

        String fileName = jobParam.getFileName();
        String filePath = tmpPath + fileName;

        if (FileUtil.exist(filePath)) {
            FileUtil.del(filePath);
        }

        minioClient = MinioClient.builder()
                .endpoint(jobParam.getEndpoint())
                .credentials(jobParam.getAccessKey(), jobParam.getSecretKey())
                .build();

        XxlJobHelper.log(logFormat.formatted("try pull resource from oss"));
        try {
            DownloadObjectArgs downloadObjectArgs = DownloadObjectArgs
                    .builder()
                    .bucket(jobParam.getBucketName())
                    .object(jobParam.getFileName())
                    .filename(filePath)
                    .build();
            minioClient.downloadObject(downloadObjectArgs);
        } catch (Exception e) {
            XxlJobHelper.log(logFormat.formatted("pull resource from oss failed"));
            throw new RuntimeException(e);
        }

        // 执行ffmpeg命令
        XxlJobHelper.log(logFormat.formatted("try execute ffmpeg"));

        String uuid = UUID.fastUUID().toString();

        ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c",
                """
                        ffmpeg -threads 0 -vsync 1 -i %s \\
                            -preset ultrafast \\
                            -lavfi '[0] scale=1280:720[hd],[0] scale=1920:1080[fhd]' \\
                            -vsync 1 \\
                            -c:v libx264 -c:a aac -b:v:0 2800k -b:a:0 128k -b:v:1 5000k -b:a:1 192k \\
                            -map '[hd]' -map 0:a -map '[fhd]' -map 0:a \\
                            -var_stream_map 'v:0,agroup:hd,name:video_hd a:0,agroup:hd,name:audio_hd v:1,agroup:fhd,name:video_fhd a:1,agroup:fhd,name:audio_fhd' \\
                            -f hls -master_pl_name %s.m3u8 \\
                            -ar 44100 -ac 2 \
                            -hls_wrap 0 \\
                            -g 120 -keyint_min 120 -sc_threshold 0 -muxpreload 0 -muxdelay 0 \\
                            -hls_time 20 -hls_flags single_file -hls_playlist_type vod -hls_list_size 0 \\
                            -hls_segment_type fmp4 -hls_segment_filename '%s%%v.mp4' %s%%v.m3u8
                        """.formatted(filePath, uuid, tmpPath, tmpPath));

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

        // 上传文件到oss
        for (File file : files) {
            XxlJobHelper.log(logFormat.formatted("try upload " + file.getName() + " to oss"));
            try {
                minioClient.uploadObject(io.minio.UploadObjectArgs.builder()
                        .bucket(jobParam.getBucketName())
                        .object(file.getName())
                        .filename(file.getAbsolutePath())
                        .build());
            } catch (Exception e) {
                XxlJobHelper.log(logFormat.formatted("upload file to oss failed"));
                throw new RuntimeException(e);
            }
        }

        // 删除oss中原始视频文件
        XxlJobHelper.log(logFormat.formatted("try delete " + fileName + " from oss"));
        try {
            minioClient.removeObject(io.minio.RemoveObjectArgs.builder()
                    .bucket(jobParam.getBucketName())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            XxlJobHelper.log(logFormat.formatted("delete file from oss failed"));
            throw new RuntimeException(e);
        }

    }

    static class JobParam {

        private String endpoint;

        private String accessKey;

        private String secretKey;

        private String bucketName;

        private String fileName;


        public JobParam() {
        }

        public JobParam(String endpoint, String accessKey, String secretKey, String bucketName, String fileName) {
            this.endpoint = endpoint;
            this.accessKey = accessKey;
            this.secretKey = secretKey;
            this.bucketName = bucketName;
            this.fileName = fileName;
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
