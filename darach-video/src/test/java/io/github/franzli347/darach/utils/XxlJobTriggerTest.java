//package io.github.franzli347.darach.utils;
//
//import io.github.franzli347.darach.config.minio.MinioProperties;
//import io.github.franzli347.model.entity.Task;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class XxlJobTriggerTest {
//
//    @Test
//    void testTriggerJob() {
//        // Setup
//        final Task task = new Task();
//        task.setId(0);
//        task.setFileName("fileName");
//        task.setBucketName("bucketName");
//        task.setStatus(0);
//        task.setTaskParam(Map.ofEntries(Map.entry("value", "value")));
//
//        when(mockMinioProperties.getEndpoint()).thenReturn("endpoint");
//        when(mockMinioProperties.getAccessKey()).thenReturn("accessKey");
//        when(mockMinioProperties.getSecretKey()).thenReturn("secretKey");
//
//        // Run the test
//        final String result = xxlJobTriggerUnderTest.triggerJob(task);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }
//}
