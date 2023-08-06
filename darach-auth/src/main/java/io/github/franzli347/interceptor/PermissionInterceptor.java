package io.github.franzli347.interceptor;

import io.github.franzli347.annotation.PermissionLimit;
import io.github.franzli347.constant.ErrorCode;
import io.github.franzli347.constant.UserLoginConstant;
import io.github.franzli347.exception.PermissionException;
import io.github.franzli347.model.UserLoginInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

@Component
public class PermissionInterceptor implements AsyncHandlerInterceptor {

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // get method permission
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        PermissionLimit annotation = handlerMethod.getMethodAnnotation(PermissionLimit.class);
        if (annotation == null) {
            annotation = handlerMethod.getBeanType().getAnnotation(PermissionLimit.class);
        }
        if (annotation == null) {
            return true;
        }
        String[] needRoles = annotation.needRole();
        if (needRoles.length == 0) {
            return true;
        }
        UserLoginInfo userLoginInfo = (UserLoginInfo) redisTemplate
                .opsForValue()
                .get(UserLoginConstant.USER_LOGIN_INFO + request.getHeader(UserLoginConstant.HTTP_AUTH_HEADER));

        if (userLoginInfo == null) {
            throw new PermissionException(ErrorCode.LOGIN_STATUS_ERROR);
        }
        // check role
        for (String needRole : needRoles) {
            if (userLoginInfo.getRoles().contains(needRole)) {
                return true;
            }
        }

        throw new PermissionException(ErrorCode.FORBIDDEN_ERROR);
    }
}
