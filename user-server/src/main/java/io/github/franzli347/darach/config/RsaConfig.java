package io.github.franzli347.darach.config;

import cn.hutool.crypto.asymmetric.RSA;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.SneakyThrows;
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
    @SneakyThrows
    @PostConstruct
    public void init(){
        RSA_PUBLIC_KEY = new ClassPathResource("rsa/public_key.pem")
                .getContentAsString(Charset.defaultCharset());
        RSA_PRIVATE_KEY = new ClassPathResource("rsa/private_key.pem")
                .getContentAsString(Charset.defaultCharset());
    }

    @Bean
    public RSA rsa(){
        return new RSA(RSA_PRIVATE_KEY,RSA_PUBLIC_KEY);
    }

}