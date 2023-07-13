package io.github.franzli347.darach.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.darach.common.userloginchain.LoginValidateChain;
import io.github.franzli347.darach.common.userloginchain.ValidatedCodeValidator;
import io.github.franzli347.darach.constant.UserRedisConstant;
import io.github.franzli347.darach.mapper.UserMapper;
import io.github.franzli347.darach.model.dto.UserDto;
import io.github.franzli347.darach.model.entity.User;
import io.github.franzli347.darach.service.UserService;
import io.github.franzli347.darach.utils.PasswordEncryptUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author franz
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-06-27 17:08:11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    ICaptcha captcha;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    LoginValidateChain loginValidateChain;

    @Resource
    PasswordEncryptUtil passwordEncryptUtil;

    @Resource
    ValidatedCodeValidator validatedCodeValidator;


    @Override
    public Map<String, Object> validatedCodeImg() {
        captcha.createCode();
        String uuid = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(UserRedisConstant.VALITED_CODE_KEY_PERFIX + uuid, captcha.getCode(),  5, TimeUnit.MINUTES);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        captcha.write(os);
        return Map.of("uuid", uuid, "img", Base64.encode(os.toByteArray()));
    }

    @Override
    public Boolean userRegister(UserDto dto) {
        validatedCodeValidator.handle(dto,true);
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncryptUtil.encrypt(dto.getPassword()));
        save(user);
        return true;
    }

    @Override
    public SaTokenInfo login(UserDto dto) {
        loginValidateChain.validate(dto);
        User u = query().eq("email", dto.getEmail()).one();
        StpUtil.login(dto.getEmail());
        StpUtil.getSession().set("loginInfo",u);
        return StpUtil.getTokenInfo();
    }
}




