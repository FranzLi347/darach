package io.github.franzli347.darach.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.captcha.ICaptcha;
import io.github.franzli347.darach.common.userloginchain.LoginValidateChain;
import io.github.franzli347.darach.common.userloginchain.ValidatedCodeValidator;
import io.github.franzli347.darach.model.dto.UserDto;
import io.github.franzli347.darach.utils.PasswordEncryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.OutputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableConfigurationProperties
@SpringBootTest(classes = {LoginValidateChain.class,
        PasswordEncryptUtil.class,
        Handle.class,
        ICaptcha.class
})
class UserServiceImplTest {

    private UserServiceImpl userServiceImplUnderTest;
    @Autowired
    private LoginValidateChain loginValidateChain;
    @Autowired
    private PasswordEncryptUtil passwordEncryptUtil;
    @Autowired
    private ValidatedCodeValidator validatedCodeValidator;
    @Autowired
    private ICaptcha captcha;

    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl();
        userServiceImplUnderTest.redisTemplate = mock(RedisTemplate.class);
        userServiceImplUnderTest.captcha = captcha;
        userServiceImplUnderTest.loginValidateChain = loginValidateChain;
        userServiceImplUnderTest.passwordEncryptUtil = passwordEncryptUtil;
        userServiceImplUnderTest.validatedCodeValidator = validatedCodeValidator;
    }

    @Test
    void testValidatedCodeImg() {
        // Setup
        when(userServiceImplUnderTest.redisTemplate.opsForValue()).thenReturn(null);
        doNothing().when(userServiceImplUnderTest.redisTemplate.opsForValue()).set(any(),any());
        when(userServiceImplUnderTest.captcha.getCode()).thenReturn("123455");

        // Run the test
        final Map<String, Object> result = userServiceImplUnderTest.validatedCodeImg();

        // Verify the results
        verify(userServiceImplUnderTest.captcha).createCode();
        verify(userServiceImplUnderTest.captcha).write(any(OutputStream.class));
    }

    @Test
    void testUserRegister() {
        // Setup
        final UserDto dto = new UserDto("email", "password", "8e1d7e03-4227-4403-ab9f-40336e98f65f", "validateCode");
        when(userServiceImplUnderTest.passwordEncryptUtil.encrypt("password")).thenReturn("password");

        // Run the test
        final Boolean result = userServiceImplUnderTest.userRegister(dto);

        // Verify the results
        assertThat(result).isTrue();
        verify(userServiceImplUnderTest.validatedCodeValidator).handle(
                new UserDto("email", "password", "8e1d7e03-4227-4403-ab9f-40336e98f65f", "validateCode"), true);
    }

    @Test
    void testLogin() {
        // Setup
        final UserDto dto = new UserDto("email", "password", "8e1d7e03-4227-4403-ab9f-40336e98f65f", "validateCode");

        // Run the test
        final SaTokenInfo result = userServiceImplUnderTest.login(dto);

        // Verify the results
        verify(userServiceImplUnderTest.loginValidateChain).validate(
                new UserDto("email", "password", "8e1d7e03-4227-4403-ab9f-40336e98f65f", "validateCode"));
    }
}
