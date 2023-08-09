package io.github.franzli347.darach.controller;

import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import io.github.franzli347.darach.model.dto.CoverTaskCreateDto;
import io.github.franzli347.darach.model.entity.VideoPath;
import io.github.franzli347.darach.service.VideoPathService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("video")
public class VideoPathController {

    VideoPathService videoPathService;

    BeanSearcher beanSearcher;


    public VideoPathController(BeanSearcher beanSearcher,VideoPathService videoPathService){
        this.beanSearcher = beanSearcher;
        this.videoPathService = videoPathService;
    }

    @GetMapping("/byAtId/{animateId}/{page}/{size}")
    public SearchResult<VideoPath> getVideoPathByAnimateId(@PathVariable Integer animateId,@PathVariable Integer page,@PathVariable Integer size) {
      return  beanSearcher.search(VideoPath.class, Map.of("animateId",animateId,"page",page,"size",size));
    }

    @GetMapping("/EpoNumsData/{animateId}")
    public List<Integer> getEpoNumsByAnimateId(@PathVariable Integer animateId) {
        return videoPathService.getEpoNumsByAnimateId(animateId);
    }

    @GetMapping("pu/{animateId}/{epoNum}")
    public String getVideoPathByAnimateIdAndEpoNum(@PathVariable Integer animateId,@PathVariable Integer epoNum) {
        return videoPathService.getVideoPathByAnimateIdAndEpoNum(animateId,epoNum);
    }

    @GetMapping("{id}")
    public VideoPath getVideoPathById(@PathVariable Integer id) {
        return videoPathService.getVideoPathById(id);
    }

    @PostMapping
    public Boolean save(@RequestBody VideoPath videoPath) {
        return videoPathService.saveVideoPath(videoPath);
    }

    @PutMapping("{id}")
    public Boolean updateVideoPath(@RequestBody VideoPath videoPath,@PathVariable Integer id) {
        videoPath.setId(id);
        return videoPathService.updateVideoPath(videoPath);
    }

    @PostMapping("/ct")
    public Boolean createVideoCoverTask(@RequestBody CoverTaskCreateDto coverTaskCreateDto) {
        return videoPathService.createVideoCoverTask(coverTaskCreateDto);
    }




}
