package com.xxl.job.executor.sample.frameless;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.executor.sample.frameless.JobHandler.FfmpgeExecutor;
import com.xxl.job.executor.sample.frameless.config.FrameLessXxlJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class FfmpegExecutorApplication {
    private static Logger logger = LoggerFactory.getLogger(FfmpegExecutorApplication.class);

    public static void main(String[] args) {

        try {
            // start
            FrameLessXxlJobConfig.getInstance().initXxlJobExecutor();
            XxlJobExecutor.registJobHandler("FfmpgeExecutor",new FfmpgeExecutor());

            // Blocks until interrupted
            while (true) {
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            // destroy
            FrameLessXxlJobConfig.getInstance().destroyXxlJobExecutor();
        }

    }

}
