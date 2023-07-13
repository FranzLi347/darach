package io.github.franzli347.darach.common.userloginchain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.franzli347.darach.common.AbstractHandler;
import io.github.franzli347.darach.constant.ErrorCode;
import io.github.franzli347.darach.exception.BusinessException;
import io.github.franzli347.darach.mapper.UserMapper;
import io.github.franzli347.darach.model.dto.UserDto;
import io.github.franzli347.darach.model.entity.User;
import io.github.franzli347.darach.utils.PasswordEncryptUtil;
import jakarta.annotation.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Franz
 * 密码校验
 */
@Component
@Order(3)
public class PasswordValidator extends AbstractHandler {

    @Resource
    private UserMapper mapper;

    @Resource
    private PasswordEncryptUtil passwordEncryptUtil;

    @Override
    public void handle(Object obj,Boolean preventNext){
        UserDto dto = (UserDto) obj;

        // 密码校验
        String password = passwordEncryptUtil.encrypt(dto.getPassword());
        String email = dto.getEmail();
        // 查询数据库密码
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);

        User user = Optional
                .ofNullable(mapper.selectOne(wrapper))
                .orElseThrow(() -> new BusinessException(ErrorCode.USERNAME_ERROR));

        String sysPassword = user.getPassword();

        if (!sysPassword.equals(password)) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        super.handle(obj,preventNext);
    }
}
