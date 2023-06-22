package io.github.franzli347.darach.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.franzli347.darach.mapper.AnimateMapper;
import io.github.franzli347.darach.model.entity.Animate;
import io.github.franzli347.darach.service.AnimateService;
import org.springframework.stereotype.Service;

/**
 * @author Franz
 * @description 针对表【animate】的数据库操作Service实现
 * @createDate 2023-06-17 07:47:28
 */
@Service
public class AnimateServiceImpl extends ServiceImpl<AnimateMapper, Animate>
        implements AnimateService {

}
