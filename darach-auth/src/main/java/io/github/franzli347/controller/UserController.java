package io.github.franzli347.controller;

import io.github.franzli347.config.capture.CaptureImgProperties;
import io.github.franzli347.model.dto.UserDto;
import io.github.franzli347.service.UserService;
import io.github.franzli347.utils.AccessLimit;
import io.github.franzli347.utils.IpUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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

    @PutMapping("/register")
//    @EncryptController(requestEncrypt = true, responseEncrypt = true)
    public Boolean userRegister(@RequestBody UserDto dto) {
        return userService.userRegister(dto);
    }

    @PostMapping("/login")
//    @EncryptController(requestEncrypt = true, responseEncrypt = true)
    public String login(@RequestBody UserDto dto, HttpServletRequest request){
        return userService.login(dto,IpUtils.getIpAddr(request),request.getHeader("User-Agent"));
    }

    @GetMapping("/addRole/{userid}/{roleid}")
    public Boolean addRole(@PathVariable("userid") Integer userid,@PathVariable("roleid") Integer roleid){
        return userService.addRole(userid,roleid);
    }

    @PostMapping("/info")
    public String userInfo(){
        return "success";
    }

}
