package io.github.franzli347.darach.common.userloginchain;

import io.github.franzli347.darach.common.AbstractHandler;
import io.github.franzli347.darach.config.CaptureImgProperties;
import io.github.franzli347.darach.constant.ErrorCode;
import io.github.franzli347.darach.constant.UserRedisConstant;
import io.github.franzli347.darach.exception.BusinessException;
import io.github.franzli347.darach.model.dto.UserDto;
import jakarta.annotation.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @author Franz
 *
 * 验证码校验
 */
@Order(1)
@Component
public class ValidatedCodeValidator extends AbstractHandler {

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private CaptureImgProperties captureImgProperties;

    @Override
    public void handle(Object obj,Boolean preventNext){
        if (Boolean.TRUE.equals(captureImgProperties.getEnable())){
            UserDto dto = (UserDto) obj;
            String uuid = Optional.ofNullable(dto.getUuid()).orElseThrow(() -> new BusinessException(ErrorCode.VALIDATED_CODE_ERROR));
            String sysCode = (String) Optional
                    .ofNullable(redisTemplate.opsForValue()
                    .get(UserRedisConstant.VALITED_CODE_KEY_PERFIX + uuid))
                    .orElseThrow(() -> new BusinessException(ErrorCode.VALIDATED_CODE_EXPIRED_ERROR));
            if (!sysCode.equals(dto.getValidateCode())) {
                throw new BusinessException(ErrorCode.VALIDATED_CODE_ERROR);
            }
            redisTemplate.delete(UserRedisConstant.VALITED_CODE_KEY_PERFIX + uuid);
        }
        super.handle(obj,preventNext);
    }
}
