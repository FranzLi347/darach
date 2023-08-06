package io.github.franzli347;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: FranzLi
 * @Description: 启动类
 */
@SpringBootApplication(scanBasePackages = "io.github.franzli347")
@EnableAsync
public class DarachApplication {
    public static void main(String[] args) {
        SpringApplication.run(DarachApplication.class, args);
    }
}
