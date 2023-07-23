package io.github.franzli347.aspect;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONUtil;
import io.github.franzli347.constant.EncConstant;
import io.github.franzli347.utils.EncryptController;
import jakarta.annotation.Resource;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
/*
 * @Author: Franz Li
 * 响应参数加密
 */
@Order(2)
@RestControllerAdvice
public class ResponseEncryptAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    RSA rsa;

    @Resource
    RedisTemplate<String,String> redisTemplate;

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
        // 设置响应头
        response.getHeaders().set("enc","true");
        String aesKey = request.getHeaders().get(EncConstant.AES_HEADER).get(0);
        // redis 优化aesKey解密
        if (Boolean.TRUE.equals(redisTemplate.hasKey(EncConstant.AES_MAP_PREFIX + aesKey))) {
            aesKey = redisTemplate.opsForValue().get(EncConstant.AES_MAP_PREFIX + aesKey);
        } else {
            aesKey = rsa.decryptStr(aesKey, KeyType.PrivateKey);
            redisTemplate.opsForValue().set(EncConstant.AES_MAP_PREFIX + aesKey, aesKey,EncConstant.CACHE_EXPIRE_HOUR,TimeUnit.HOURS);
        }
        // aes加密响应
        AES aes = new AES(EncConstant.AES_MOD,
                EncConstant.AES_PADDING,
                aesKey.getBytes(StandardCharsets.UTF_8),
                EncConstant.AES_IV.getBytes(StandardCharsets.UTF_8));
        String content = JSONUtil.toJsonStr(body);
        return aes.encryptBase64(content, StandardCharsets.UTF_8);
    }
}
