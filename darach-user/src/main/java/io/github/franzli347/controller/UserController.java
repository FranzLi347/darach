package io.github.franzli347.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import io.github.franzli347.config.capture.CaptureImgProperties;
import io.github.franzli347.model.dto.UserDto;
import io.github.franzli347.service.UserService;
import io.github.franzli347.utils.AccessLimit;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @Resource
    CaptureImgProperties captureImgProperties;

    @GetMapping("captureImg")
    @AccessLimit(limit = 1,sec = 10)
    public Map<String,Object> validatedCodeImg() {
       if (Boolean.FALSE.equals(captureImgProperties.getEnable())){
           return Map.of("msg","验证码功能未开启");
       }
        return userService.validatedCodeImg();
    }

    @PutMapping
//    @EncryptController(requestEncrypt = true, responseEncrypt = true)
    public Boolean userRegister(@RequestBody UserDto dto) {
        return userService.userRegister(dto);
    }

    @PostMapping
    public SaTokenInfo login(@RequestBody UserDto dto) {
        return userService.login(dto);
    }

}
