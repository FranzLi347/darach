package io.github.franzli347.darach.common;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import io.github.franzli347.darach.config.CaptureImgProperties;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.OutputStream;

@Configuration
public class CaptureConfig {

    @Resource
    private CaptureImgProperties captureImgProperties;


    @Bean
    public ICaptcha captcha(){
        if (Boolean.FALSE.equals(captureImgProperties.getEnable())){
            return new ICaptcha() {
                @Override
                public void createCode() {
                    throw new RuntimeException("验证码功能未开启");
                }

                @Override
                public String getCode() {
                    return null;
                }

                @Override
                public boolean verify(String userInputCode) {
                    return false;
                }

                @Override
                public void write(OutputStream out) {

                }
            };
        }
        switch (captureImgProperties.getType()){
            case "LineCaptcha" -> {
                return new LineCaptcha(captureImgProperties.getWidth(),captureImgProperties.getHeight());
            }
            case "CircleCaptcha" -> {
                return new CircleCaptcha(captureImgProperties.getWidth(),captureImgProperties.getHeight());
            }
            case "ShearCaptcha" -> {
                return new ShearCaptcha(captureImgProperties.getWidth(),captureImgProperties.getHeight());
            }
            default -> throw new RuntimeException("不支持的验证码类型");
        }


    }

}
