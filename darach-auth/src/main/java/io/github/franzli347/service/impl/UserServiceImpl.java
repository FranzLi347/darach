package io.github.franzli347.service.impl;

import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.util.IdUtil;
import io.github.franzli347.common.userloginchain.LoginValidateChain;
import io.github.franzli347.common.userloginchain.ValidatedCodeValidator;
import io.github.franzli347.constant.UserRedisConstant;
import io.github.franzli347.model.UserLoginInfo;
import io.github.franzli347.model.dto.UserDto;
import io.github.franzli347.model.entity.RoleEntity;
import io.github.franzli347.model.entity.UserEntity;
import io.github.franzli347.model.entity.UserRoleMap;
import io.github.franzli347.repository.UserRepository;
import io.github.franzli347.repository.UserRoleMapRepository;
import io.github.franzli347.service.UserService;
import io.github.franzli347.util.MyJwtUtil;
import io.github.franzli347.util.PasswordEncryptUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author franz
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-06-27 17:08:11
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    ICaptcha captcha;

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Resource
    LoginValidateChain loginValidateChain;

    @Resource
    PasswordEncryptUtil passwordEncryptUtil;

    @Resource
    ValidatedCodeValidator validatedCodeValidator;

    @Resource
    UserRepository userRepository;

    @Resource
    MyJwtUtil myJwtUtil;

    @Resource
    UserRoleMapRepository userRoleMapRepository;


    @Override
    public Map<String, Object> validatedCodeImg() {
        captcha.createCode();
        String uuid = IdUtil.simpleUUID();
        redisTemplate.opsForValue()
                .set(UserRedisConstant.VALITED_CODE_KEY_PERFIX + uuid, captcha.getCode(),  5, TimeUnit.MINUTES);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        captcha.write(os);
        return Map.of("uuid", uuid, "img", Base64.encode(os.toByteArray()));
    }

    @Override
    public Boolean userRegister(UserDto dto) {
        validatedCodeValidator.handle(dto,true);
        String email = dto.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
            return false;
        }
        userEntity = new UserEntity();
        userEntity.setEmail(dto.getEmail());
        userEntity.setPassword(passwordEncryptUtil.encrypt(dto.getPassword()));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public String login(UserDto dto, String ip,String userAgent) {
        loginValidateChain.validate(dto);
        // get user by email
        UserEntity u = userRepository.findByEmail(dto.getEmail());
        String jwt = myJwtUtil.createJwt(u);
        UserLoginInfo userLoginInfo = getUserLoginInfo(u,ip,userAgent);
        redisTemplate.opsForValue().set("user:login:token:"+jwt,userLoginInfo,60 * 72,TimeUnit.MINUTES);
        return jwt;
    }

    @Override
    public Boolean addRole(Integer userid, Integer roleid) {
        userRoleMapRepository.save(new UserRoleMap(userid,roleid));
        return Boolean.TRUE;
    }


    private UserLoginInfo getUserLoginInfo(UserEntity u, String ip,String userAgent){
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setId(u.getId());
        userLoginInfo.setEmail(u.getEmail());
        userLoginInfo.setLoginIp(ip);
        userLoginInfo.setLoginUA(userAgent);
        userLoginInfo.setLoginTime(LocalDateTime.now());
        userLoginInfo.setRoles(u.getRoleEntities().stream().map(RoleEntity::getRoleName).collect(Collectors.toSet()));
        return userLoginInfo;
    }

}




