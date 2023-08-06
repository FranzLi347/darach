package io.github.franzli347.common.userloginchain;

import io.github.franzli347.common.Handler;
import io.github.franzli347.model.dto.UserDto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


/**
 * @author Franz
 * 登录校验链
 */
@Component
public class LoginValidateChain {

    @Resource
    List<Handler> chain = new LinkedList<>();

    @PostConstruct
    public void setChainNext(){
        // 初始化链
        for (int i = 0; i < chain.size() - 1; i++) {
            chain.get(i).setNext(chain.get(i + 1));
        }
    }

    public void validate(UserDto userDto){
        chain.get(0).handle(userDto,false);
    }

}
