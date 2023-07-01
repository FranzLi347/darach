package io.github.franzli347.darach.controller;

import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.util.IdUtil;
import io.github.franzli347.darach.common.ErrorCode;
import io.github.franzli347.darach.common.ResponseResult;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    RedisTemplate redisTemplate;

    private static final String VALITED_CODE_KEY_PERFIX = "sgin:";

    private static ShearCaptcha captcha = new ShearCaptcha(100, 45, 4, 4);

    static {
        captcha.setGenerator(new MathGenerator());
    }

    @GetMapping("captureImg")
    public ResponseEntity valitedCodeImg(HttpServletRequest request, HttpServletResponse response) {
        captcha.createCode();
        String code = captcha.getCode();
        String uuid = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(VALITED_CODE_KEY_PERFIX + uuid, code);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        captcha.write(os);
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("img", Base64.encode(os.toByteArray()));
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ErrorCode.SUCCESS.getCode());
        responseResult.setData(map);
        return ResponseEntity.ok(responseResult);
    }
}
