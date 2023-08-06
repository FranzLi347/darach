package io.github.franzli347.darach.service;

import io.github.franzli347.darach.model.dto.AnimateDto;

/**
* @author Franz
* @description 针对表【animate】的数据库操作Service
* @createDate 2023-06-17 07:47:28
*/

public interface AnimateService  {


    Boolean addNewAnimate(AnimateDto dto);

    Boolean updateAnimate(AnimateDto dto, Integer id);

    Boolean deleteAnimate(Integer id);
}
