package io.github.franzli347.darach.utils;

import cn.hutool.json.JSONUtil;
import io.github.franzli347.darach.common.ResponseResult;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author Franz
 * @Description: 限流 拦截器
 **/
@Component
@Slf4j
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate redisTemplate;

    private final String ACCESS_LIMIT_KEY_PREFIX = "accessLimit:";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            // class 标注优先级小于 method annotation
            Class<?> declaringClass = method.getDeclaringClass();
            AccessLimit classAccessLimit = declaringClass.getAnnotation(AccessLimit.class);
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (accessLimit == null && classAccessLimit == null) {
                return true;
            }
            int limit = accessLimit == null ? classAccessLimit.limit() : accessLimit.limit();
            int sec = accessLimit == null ? classAccessLimit.sec() : accessLimit.sec();
            String key = IpUtils.getIpAddr(request) + ":" + request.getRequestURI();
            Integer maxLimit = (Integer) redisTemplate.opsForValue().get(ACCESS_LIMIT_KEY_PREFIX + key);
            if (maxLimit == null) {
                //set时一定要加过期时间
                redisTemplate.opsForValue().set(ACCESS_LIMIT_KEY_PREFIX + key, 1, sec, TimeUnit.SECONDS);
            } else if (maxLimit < limit) {
                redisTemplate.opsForValue().set(ACCESS_LIMIT_KEY_PREFIX + key, maxLimit + 1, sec, TimeUnit.SECONDS);
            } else {
                log.error("ip: {} time: {} url:{} 触发限流", IpUtils.getIpAddr(request), LocalDateTime.now(),request.getRequestURI());
                output(response, JSONUtil.toJsonStr(ResponseResult.error("请求频繁,稍后再试!")));
                return false;
            }
        }
        return true;
    }

    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
