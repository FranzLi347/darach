package io.github.franzli347.aspect;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import io.github.franzli347.constant.EncConstant;
import io.github.franzli347.exception.BusinessException;
import io.github.franzli347.utils.EncryptController;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author:Franz Li
 * 请求参数解密
 */
@ControllerAdvice
@Slf4j
public class RequestEncryptAdvice extends RequestBodyAdviceAdapter {

    @Resource
    RSA rsa;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        // 判断是否有@EncryptController注解
        EncryptController methodAnnotation = methodParameter.getMethodAnnotation(EncryptController.class);
        return methodAnnotation != null && methodAnnotation.requestEncrypt();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        // 请求头中是否有aesKey
        if (inputMessage.getHeaders().get(EncConstant.AES_HEADER).size() > 0) {
            return decryptBody(inputMessage);
        }
        throw new BusinessException(50008, "加密参数异常");
    }

    private HttpInputMessage decryptBody(HttpInputMessage inputMessage) {
        // 用rsa解密aesKey
        String aesKey = Objects.requireNonNull(inputMessage.getHeaders().get(EncConstant.AES_HEADER)).get(0);
        // redis优化,缓存aes解密结果
        if (Boolean.TRUE.equals(redisTemplate.hasKey(EncConstant.AES_MAP_PREFIX + aesKey))) {
            aesKey = (String) redisTemplate.opsForValue().get(EncConstant.AES_MAP_PREFIX + aesKey);
        } else {
            aesKey = rsa.decryptStr(aesKey, KeyType.PrivateKey);
            redisTemplate.opsForValue().set(EncConstant.AES_MAP_PREFIX + aesKey, aesKey, EncConstant.CACHE_EXPIRE_HOUR,
                    TimeUnit.HOURS);
        }

        try {
            byte[] body = new byte[inputMessage.getBody().available()];
            inputMessage.getBody().read(body);
            String bodyStr = new String(body);

            AES aes = new AES(EncConstant.AES_MOD,
                    EncConstant.AES_PADDING,
                    aesKey.getBytes(StandardCharsets.UTF_8),
                    EncConstant.AES_IV.getBytes(StandardCharsets.UTF_8));

            log.debug("bodyStr:{}", bodyStr);

            bodyStr = aes.decryptStr(bodyStr, CharsetUtil.CHARSET_UTF_8);
            byte[] decrypt = bodyStr.getBytes(StandardCharsets.UTF_8);
            final ByteArrayInputStream bais = new ByteArrayInputStream(decrypt);
            return new HttpInputMessage() {
                @Override
                public InputStream getBody() {
                    return bais;
                }

                @Override
                public HttpHeaders getHeaders() {
                    return inputMessage.getHeaders();
                }
            };
        } catch (Exception e) {
            log.error("解密失败 {}", e);
            throw new BusinessException(50000, "加密参数异常");
        }

    }

}
