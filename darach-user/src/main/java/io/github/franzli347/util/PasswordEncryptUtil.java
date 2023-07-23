package io.github.franzli347.util;

import cn.hutool.crypto.SecureUtil;
import io.github.franzli347.config.password.PasswordEncryptProperties;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class PasswordEncryptUtil {

    @Resource
    private PasswordEncryptProperties passwordEncryptProperties;

    @SneakyThrows
    public String encrypt(String password)  {
        String algorithm = passwordEncryptProperties.getAlgorithm();
        Method method = SecureUtil.class.getMethod(algorithm, String.class);
        String invoke = (String) method
                .invoke(null, passwordEncryptProperties.getSalt() + password + passwordEncryptProperties.getSalt());
        return invoke;
    }

}
