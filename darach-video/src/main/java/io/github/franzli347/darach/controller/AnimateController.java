package io.github.franzli347.darach.controller;

import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.util.MapUtils;
import io.github.franzli347.darach.model.dto.AnimateDto;
import io.github.franzli347.darach.model.dto.HomePageVideoData;
import io.github.franzli347.darach.model.vo.AnimateVo;
import io.github.franzli347.darach.service.AnimateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Franz Li
 */
@RestController
@RequestMapping("animate")
@Slf4j
public class AnimateController {

    AnimateService animateService;

    BeanSearcher beanSearcher;


    public AnimateController(AnimateService animateService,BeanSearcher beanSearcher){
        this.animateService = animateService;
        this.beanSearcher = beanSearcher;
    }

    @GetMapping("page")
    public SearchResult<AnimateVo> query(HttpServletRequest request){
        return beanSearcher.search(AnimateVo.class, MapUtils.flat(request.getParameterMap()));
    }


    @GetMapping("homePageVideoData")
    public HomePageVideoData getHomePageVideoData() {
        return animateService.getHomePageVideoData();
    }

    @GetMapping("{id}")
    public AnimateVo getDetail(@PathVariable Integer id) {
        return beanSearcher.searchFirst(AnimateVo.class, Map.of("id",id));
    }

    @PostMapping
    public Boolean addAnimate(@RequestBody AnimateDto dto) {
        return animateService.addNewAnimate(dto);
    }

    @PutMapping("{id}")
    public Boolean updateAnimate(@RequestBody AnimateDto dto,@PathVariable Integer id) {
        return animateService.updateAnimate(dto,id);
    }

    @DeleteMapping("{id}")
    public Boolean deleteAnimate(@PathVariable Integer id) {
        return animateService.deleteAnimate(id);
    }



}
