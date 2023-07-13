package io.github.franzli347.darach.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.franzli347.darach.model.dto.AnimateCreateDto;
import io.github.franzli347.darach.model.entity.Animate;

/**
* @author Franz
* @description 针对表【animate】的数据库操作Service
* @createDate 2023-06-17 07:47:28
*/

public interface AnimateService extends IService<Animate> {


    Boolean addNewAnimate(AnimateCreateDto dto);
}
