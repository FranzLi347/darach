package io.github.franzli347.darach.aspect;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONUtil;
import io.github.franzli347.darach.utils.EncryptController;
import jakarta.annotation.Resource;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.nio.charset.Charset;

@Order(2)
@RestControllerAdvice
public class ResponseEncryptAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    RSA rsa;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        EncryptController methodAnnotation = returnType.getMethodAnnotation(EncryptController.class);
        return methodAnnotation != null && methodAnnotation.responseEncrypt();
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        return rsa.encryptBase64(JSONUtil.toJsonStr(body), Charset.defaultCharset(), KeyType.PrivateKey);
    }
}
