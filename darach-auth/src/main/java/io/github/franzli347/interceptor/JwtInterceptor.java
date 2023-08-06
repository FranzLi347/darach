package io.github.franzli347.interceptor;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import io.github.franzli347.annotation.PermissionLimit;
import io.github.franzli347.config.jwt.JwtConfig;
import io.github.franzli347.constant.ErrorCode;
import io.github.franzli347.constant.UserLoginConstant;
import io.github.franzli347.exception.LoginStatusException;
import io.github.franzli347.model.entity.UserEntity;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.nio.charset.StandardCharsets;

@Component
public class JwtInterceptor implements AsyncHandlerInterceptor {

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private RedisTemplate<String,UserEntity> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // get method permission
        if (! (handler instanceof HandlerMethod handlerMethod)){
            return true;
        }
        PermissionLimit annotation = handlerMethod.getMethodAnnotation(PermissionLimit.class);
        if (annotation == null){
            annotation = handlerMethod.getBeanType().getAnnotation(PermissionLimit.class);
        }
        if (annotation == null){
            return true;
        }
        boolean limit = annotation.limit();
        if (!limit){
            return true;
        }
        try {
            String authorization = request.getHeader(UserLoginConstant.HTTP_AUTH_HEADER);
            JWT jwt = JWT.of(authorization);
            if (StrUtil.isBlankIfStr(authorization)) {
                throw new LoginStatusException(ErrorCode.LOGIN_STATUS_ERROR);
            }
            JWTSigner hs256 = JWTSignerUtil.createSigner(UserLoginConstant.JWT_ALGORITHM, jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
            boolean verify = jwt.verify(hs256);
            if (!verify) {
                throw new LoginStatusException(ErrorCode.LOGIN_STATUS_ERROR);
            }
            JWTValidator.of(authorization).validateDate();
            if (Boolean.FALSE.equals(redisTemplate.hasKey(UserLoginConstant.USER_LOGIN_INFO + authorization))){
                throw new LoginStatusException(ErrorCode.LOGIN_STATUS_ERROR);
            }
        } catch (ValidateException e) {
            throw new LoginStatusException(ErrorCode.LOGIN_STATUS_ERROR);
        }
        return true;
    }

}
