package io.github.franzli347.darach.service;

import io.github.franzli347.darach.model.dto.AnimateCreateDto;

/**
* @author Franz
* @description 针对表【animate】的数据库操作Service
* @createDate 2023-06-17 07:47:28
*/

public interface AnimateService  {


    Boolean addNewAnimate(AnimateCreateDto dto);
}
