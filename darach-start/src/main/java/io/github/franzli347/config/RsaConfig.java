package io.github.franzli347.config;

import cn.hutool.crypto.asymmetric.RSA;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.Charset;

/**
 * @Description: rsa配置
 * @Author: FranzLi
 */
@Data
@Configuration
public class RsaConfig {

    private String RSA_PUBLIC_KEY;

    private String RSA_PRIVATE_KEY;

    @Value("${rsa.public-key-path}")
    private String RSA_PUBLIC_KEY_PATH;

    @Value("${rsa.private-key-path}")
    private String RSA_PRIVATE_KEY_PATH;
    
    @SneakyThrows
    @PostConstruct
    public void init(){
        RSA_PUBLIC_KEY = new ClassPathResource(RSA_PUBLIC_KEY_PATH)
                .getContentAsString(Charset.defaultCharset());
        RSA_PRIVATE_KEY = new ClassPathResource(RSA_PRIVATE_KEY_PATH)
                .getContentAsString(Charset.defaultCharset());
    }

    @Bean
    public RSA rsa(){
        return new RSA(RSA_PRIVATE_KEY,RSA_PUBLIC_KEY);
    }

}
