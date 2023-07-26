package io.github.franzli347.common.userloginchain;

import io.github.franzli347.common.AbstractHandler;
import io.github.franzli347.constant.ErrorCode;
import io.github.franzli347.exception.BusinessException;
import io.github.franzli347.model.dto.UserDto;
import io.github.franzli347.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Franz
 *
 * 用户名校验
 */
@Component
@Order(2)
public class UserNameValidator extends AbstractHandler {

    @Resource
    UserRepository userRepository;

    @Override
    public void handle(Object obj,Boolean preventNext){
        UserDto dto = (UserDto) obj;
        String email = dto.getEmail();
        Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new BusinessException(ErrorCode.USERNAME_ERROR));
        super.handle(obj,preventNext);
    }
}
