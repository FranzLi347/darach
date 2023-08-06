package io.github.franzli347.service;

import io.github.franzli347.model.dto.UserDto;

import java.util.Map;

/**
* @author franz
* @description 针对表【user】的数据库操作Service
* @createDate 2023-06-27 17:08:11
*/
public interface UserService  {

    Map<String, Object> validatedCodeImg();

    Boolean userRegister(UserDto dto);

    String login(UserDto dto, String ip,String userAgent);


    Boolean addRole(Integer userid, Integer roleid);
}
