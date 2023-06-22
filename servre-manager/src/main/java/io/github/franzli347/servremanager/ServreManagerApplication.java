package io.github.franzli347.servremanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServreManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServreManagerApplication.class, args);
    }

}
