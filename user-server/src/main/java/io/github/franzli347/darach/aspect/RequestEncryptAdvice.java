package io.github.franzli347.darach.aspect;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONUtil;
import io.github.franzli347.darach.exception.BusinessException;
import io.github.franzli347.darach.utils.EncryptController;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
@Slf4j
public class RequestEncryptAdvice extends RequestBodyAdviceAdapter {

    @Resource
    RSA rsa;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        EncryptController methodAnnotation = methodParameter.getMethodAnnotation(EncryptController.class);
        return methodAnnotation != null && methodAnnotation.requestEncrypt();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
                                           MethodParameter parameter,
                                           Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if (inputMessage.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON)) {
            return decryptBody(inputMessage);
        }
        return inputMessage;
    }


    private HttpInputMessage decryptBody(HttpInputMessage inputMessage){
        try{
            byte[] body = new byte[inputMessage.getBody().available()];
            inputMessage.getBody().read(body);
            String bodyStr = new String(body);
            log.debug("bodyStr:{}", bodyStr);
            bodyStr = rsa.decryptStr(bodyStr, KeyType.PrivateKey);
            byte[] decrypt = JSONUtil.toJsonStr(bodyStr).getBytes(StandardCharsets.UTF_8);
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
        }catch (Exception e){
            log.error("解密失败", e);
            throw new BusinessException(50000,"解密异常");
        }
    }

}
