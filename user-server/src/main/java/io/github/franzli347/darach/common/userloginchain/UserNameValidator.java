package io.github.franzli347.darach.common.userloginchain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.franzli347.darach.common.AbstractHandler;
import io.github.franzli347.darach.constant.ErrorCode;
import io.github.franzli347.darach.exception.BusinessException;
import io.github.franzli347.darach.mapper.UserMapper;
import io.github.franzli347.darach.model.dto.UserDto;
import io.github.franzli347.darach.model.entity.User;
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
    private UserMapper mapper;

    @Override
    public void handle(Object obj,Boolean preventNext){
        UserDto dto = (UserDto) obj;
        String email = dto.getEmail();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        Optional.ofNullable(mapper.selectOne(wrapper))
                .orElseThrow(() -> new BusinessException(ErrorCode.USERNAME_ERROR));

        super.handle(obj,preventNext);
    }
}
