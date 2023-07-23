package io.github.franzli347.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.franzli347.result.ResponseResult;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * @author Franz
 * 响应切面
 */
//顺序为第一个
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports( MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o,
                                   MethodParameter methodParameter,
                                   MediaType mediaType,
                                   Class<? extends HttpMessageConverter<?>> aClass,
                                   ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (o instanceof ResponseResult) {
            return o;
        }
        if(o instanceof String){
            return objectMapper.writeValueAsString(ResponseResult.success(o));
        }
        return ResponseResult.success(o);
    }
}
