package io.github.franzli347.util;

import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import io.github.franzli347.config.jwt.JwtConfig;
import io.github.franzli347.model.entity.UserEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class MyJwtUtil {

    @Resource
    private JwtConfig jwtConfig;

    public String createJwt(UserEntity userEntity){
        return JWTUtil.createToken(Map.of("id", userEntity.getId(),
                "exp", System.currentTimeMillis() + Long.parseLong(jwtConfig.getExpiration()))
                ,JWTSignerUtil.createSigner("HS256", jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)));
    }
}
