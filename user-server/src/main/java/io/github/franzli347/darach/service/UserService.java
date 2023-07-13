package io.github.franzli347.darach.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.darach.model.dto.UserDto;
import io.github.franzli347.darach.model.entity.User;

import java.util.Map;

/**
* @author franz
* @description 针对表【user】的数据库操作Service
* @createDate 2023-06-27 17:08:11
*/
public interface UserService extends IService<User> {

    Map<String, Object> validatedCodeImg();

    Boolean userRegister(UserDto dto);

    SaTokenInfo login(UserDto dto);


}
