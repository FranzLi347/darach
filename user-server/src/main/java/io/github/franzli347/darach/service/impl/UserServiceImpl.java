package io.github.franzli347.darach.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.darach.mapper.UserMapper;
import io.github.franzli347.darach.model.entity.User;
import io.github.franzli347.darach.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author franz
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-06-27 17:08:11
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




