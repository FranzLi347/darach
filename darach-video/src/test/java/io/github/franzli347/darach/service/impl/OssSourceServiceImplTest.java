package io.github.franzli347.darach.service.impl;

import io.github.franzli347.darach.config.minio.MinioProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {MinioProperties.class})
@Slf4j
@EnableConfigurationProperties
class OssSourceServiceImplTest {

    @Mock
    private MinioClient mockMinioClient;

    @Autowired
    @Qualifier("minioProperties")
    private MinioProperties mockMinioProperties;

    private OssSourceServiceImpl ossSourceServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        ossSourceServiceImplUnderTest = new OssSourceServiceImpl(mockMinioClient, mockMinioProperties);
    }

    @Test
    void testGetTempUrl() throws Exception {
        // Setup
        log.info("mockMinioProperties = {}", mockMinioProperties);
        log.info("mockMinioProperties.getBucketName() = {}", mockMinioProperties.getBucketName());
        when(mockMinioClient.getPresignedObjectUrl(
                any(io.minio.GetPresignedObjectUrlArgs.class)
        )).thenReturn("https://example.com/example/fileName");
        // Run the test
        final String result = ossSourceServiceImplUnderTest.getTempUrl("fileName");

        // Verify the results
        assertThat(result).isEqualTo("https://example.com/example/fileName");
    }

}
