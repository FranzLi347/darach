package io.github.franzli347.common.userloginchain;

import io.github.franzli347.common.AbstractHandler;
import io.github.franzli347.constant.ErrorCode;
import io.github.franzli347.exception.BusinessException;
import io.github.franzli347.model.dto.UserDto;
import io.github.franzli347.model.entity.User;
import io.github.franzli347.repository.UserRepository;
import io.github.franzli347.util.PasswordEncryptUtil;
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
    UserRepository userRepository;

    @Resource
    private PasswordEncryptUtil passwordEncryptUtil;

    @Override
    public void handle(Object obj,Boolean preventNext){
        UserDto dto = (UserDto) obj;

        // 密码校验
        String password = passwordEncryptUtil.encrypt(dto.getPassword());
        String email = dto.getEmail();
        // 查询数据库密码

        User user = Optional
                .ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new BusinessException(ErrorCode.USERNAME_ERROR));

        String sysPassword = user.getPassword();

        if (!sysPassword.equals(password)) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
        super.handle(obj,preventNext);
    }
}
