package com.xxl.job.executorbiz;

import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.client.ExecutorBizClient;
import com.xxl.job.core.biz.model.*;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * executor api test
 * <p>
 * Created by xuxueli on 17/5/12.
 */
public class ExecutorBizTest {

    // admin-client
    private static String addressUrl = "http://127.0.0.1:9999/";
    private static String accessToken = null;

    @Test
    public void beat() throws Exception {
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);
        // Act
        final ReturnT<String> retval = executorBiz.beat();

        // Assert result
        Assertions.assertNotNull(retval);
        Assertions.assertNull(((ReturnT<String>) retval).getContent());
        Assertions.assertEquals(200, retval.getCode());
        Assertions.assertNull(retval.getMsg());
    }

    @Test
    public void idleBeat() {
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        final int jobId = 0;

        // Act
        final ReturnT<String> retval = executorBiz.idleBeat(new IdleBeatParam(jobId));

        // Assert result
        Assertions.assertNotNull(retval);
        Assertions.assertNull(((ReturnT<String>) retval).getContent());
        Assertions.assertEquals(500, retval.getCode());
        Assertions.assertEquals("job thread is running or has trigger queue.", retval.getMsg());
    }

    private TriggerParam getTriggerParam() {
        TriggerParam triggerParam = new TriggerParam();
        // 任务ID
        triggerParam.setJobId(5);
        // 任务标识
        triggerParam.setExecutorHandler("FfmpgeExecutor");
        // 任务参数
        triggerParam.setExecutorParams("{\n" +
                "                    \"endpoint\":\"http://localhost:9000\",\n" +
                "                    \"accessKey\":\"minioadmin\",\n" +
                "                    \"secretKey\":\"minioadmin\",\n" +
                "                    \"bucketName\":\"animate\",\n" +
                "                    \"fileName\":\"进击的巨人.第一季.25.中日双语.HDTV.1080P.甜饼字幕组.mp4\"\n" +
                "                }");
        // 任务阻塞策略，可选值参考 com.xxl.job.core.enums.ExecutorBlockStrategyEnum
        triggerParam.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.COVER_EARLY.name());
        // 任务模式，可选值参考 com.xxl.job.core.glue.GlueTypeEnum
        triggerParam.setGlueType(GlueTypeEnum.BEAN.name());
        // GLUE脚本代码
        triggerParam.setGlueSource(null);
        // GLUE脚本更新时间，用于判定脚本是否变更以及是否需要刷新
        triggerParam.setGlueUpdatetime(System.currentTimeMillis());
        // 本次调度日志ID
        triggerParam.setLogId(triggerParam.getJobId());
        // 本次调度日志时间
        triggerParam.setLogDateTime(System.currentTimeMillis());
        return triggerParam;

    }

    @Test
    public void run() {
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

//        triggerParam.set
        // Act
        final ReturnT<String> retval = executorBiz.run(getTriggerParam());
        // Assert result
        Assertions.assertNotNull(retval);
    }

    @Test
    public void kill() {
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        final int jobId = 0;

        // Act
        final ReturnT<String> retval = executorBiz.kill(new KillParam(jobId));

        // Assert result
        Assertions.assertNotNull(retval);
        Assertions.assertNull(((ReturnT<String>) retval).getContent());
        Assertions.assertEquals(200, retval.getCode());
        Assertions.assertNull(retval.getMsg());
    }

    @Test
    public void log() {
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        final long logDateTim = 0L;
        final long logId = 0;
        final int fromLineNum = 0;

        // Act
        final ReturnT<LogResult> retval = executorBiz.log(new LogParam(logDateTim, logId, fromLineNum));

        // Assert result
        Assertions.assertNotNull(retval);
    }

}
