package io.github.franzli347.darach.controller;

import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.util.MapUtils;
import io.github.franzli347.darach.model.dto.AnimateCreateDto;
import io.github.franzli347.darach.model.vo.AnimateVo;
import io.github.franzli347.darach.service.AnimateService;
import io.github.franzli347.darach.utils.EncryptController;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Franz Li
 */
@RestController
@RequestMapping("/animate")
@Slf4j
public class AnimateController {

    @Resource
    AnimateService animateService;

    @Resource
    BeanSearcher beanSearcher;

    @GetMapping
    @EncryptController(responseEncrypt = true)
    public SearchResult<AnimateVo> query(HttpServletRequest request){
        SearchResult<AnimateVo> search = beanSearcher.search(AnimateVo.class, MapUtils.flat(request.getParameterMap()));
        log.debug(String.valueOf(search));
        return search;
    }

    @GetMapping("episodeNum")
    public Number getEpisodeNum(Integer id) {
        return beanSearcher.searchCount(AnimateVo.class, Map.of("id",id));
    }

    @GetMapping("/detail")
    @EncryptController(responseEncrypt = true)
    public AnimateVo getDetail(Integer id) {
        return beanSearcher.searchFirst(AnimateVo.class, Map.of("id",id));
    }

    @PutMapping
    public Boolean addNewAnimate(@RequestBody AnimateCreateDto dto) {
        return animateService.addNewAnimate(dto);
    }
}
