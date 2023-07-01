package io.github.franzli347.darach.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.franzli347.darach.common.PageRequestEntity;
import io.github.franzli347.darach.constant.CommonConstant;
import io.github.franzli347.darach.model.dto.AnimateCreateDto;
import io.github.franzli347.darach.model.dto.EpisodeDto;
import io.github.franzli347.darach.model.entity.Animate;
import io.github.franzli347.darach.model.entity.VedioPath;
import io.github.franzli347.darach.model.vo.AnimateVo;
import io.github.franzli347.darach.model.vo.PageInfoVo;
import io.github.franzli347.darach.service.AnimateService;
import io.github.franzli347.darach.service.VedioPathService;
import io.github.franzli347.darach.utils.XxlJobTrigger;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Franz Li
 */
@RestController
@RequestMapping("/animate")
public class AnimateController {

    @Resource
    AnimateService animateService;

    @Resource
    VedioPathService vedioPathService;

    @Resource
    XxlJobTrigger xxlJobTrigger;

    @PostMapping("/list")
    public ResponseEntity<PageInfoVo<?>> list(@RequestBody PageRequestEntity entity) {
        Page<Animate> p = new Page<>(entity.getCurrent(), entity.getPageSize());
        QueryWrapper<Animate> wrapper = new QueryWrapper<>();
        //if not contain 'order by' in sql, the order by will be ignored
        wrapper.orderBy(StrUtil.isNotBlank(entity.getSortField()),
                CommonConstant.SORT_ORDER_ASC.equals(entity.getSortOrder()),
                entity.getSortField());
        animateService.page(p, wrapper);
        PageInfoVo<List<?>> pageInfoVo = new PageInfoVo<>();
        BeanUtils.copyProperties(p, pageInfoVo);
        // convert entity to vo
        var list = pageInfoVo.getRecords().stream().map((animate -> {
            AnimateVo vo = new AnimateVo();
            BeanUtils.copyProperties(animate, vo);
            return vo;
        })).toList();
        pageInfoVo.setRecords(list);
        return ResponseEntity.ok(pageInfoVo);
    }

    @GetMapping("/episodenum/{id}")
    public ResponseEntity<Long> getEpisodeNum(@PathVariable Integer id) {
        Long episodeNum = vedioPathService.lambdaQuery()
                .eq(VedioPath::getAnimateId, id)
                .count();
        return ResponseEntity.ok(episodeNum);}

    @GetMapping("/detail/{id}")
    public ResponseEntity<AnimateVo> getDetail(@PathVariable Integer id) {
        Animate animate = animateService.getById(id);
        AnimateVo vo = new AnimateVo();
        BeanUtils.copyProperties(animate, vo);
        return ResponseEntity.ok(vo);
    }

    @PutMapping
    public ResponseEntity<String> addNewAnimate(@RequestBody AnimateCreateDto dto) {
        Animate animate = new Animate();
        BeanUtils.copyProperties(dto, animate);
        animateService.save(animate);
        List<EpisodeDto> episodeList = dto.getEpisodeList();
        List<VedioPath> insertData = new ArrayList<>();
        for (EpisodeDto episodeDto : episodeList) {
            VedioPath vedioPath = new VedioPath();
            vedioPath.setAnimateId(animate.getId());
            vedioPath.setFileName(episodeDto.getFileName());
            xxlJobTrigger.triggerJob(episodeDto.getFileName());
            vedioPath.setEpisode(String.valueOf(episodeDto.getEpisode()));
            insertData.add(vedioPath);
        }
        vedioPathService.saveBatch(insertData);
        return ResponseEntity.ok("success");
    }


}
