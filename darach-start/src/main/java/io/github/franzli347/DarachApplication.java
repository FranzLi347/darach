package io.github.franzli347;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: FranzLi
 * @Description: 启动类
 */
@SpringBootApplication(scanBasePackages = "io.github.franzli347")
@MapperScan("io.github.franzli347.mapper")
public class DarachApplication {
    public static void main(String[] args) {
        SpringApplication.run(DarachApplication.class, args);
    }
}
