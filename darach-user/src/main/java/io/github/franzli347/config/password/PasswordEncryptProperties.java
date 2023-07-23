package io.github.franzli347.config.password;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Franz
 * 密码加密配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "password-encrypt")
public class PasswordEncryptProperties {
    Boolean enable;
    String algorithm;
    String salt;
}
