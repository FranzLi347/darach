package io.github.franzli347.darach.controller;

import io.github.franzli347.darach.model.dto.CoverTaskCreateDto;
import io.github.franzli347.darach.model.entity.VideoPath;
import io.github.franzli347.darach.repository.VideoPathRepository;
import io.github.franzli347.darach.service.VideoPathService;
import io.github.franzli347.model.entity.Task;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("video")
public class VideoPathController {

    @Resource
    VideoPathService videoPathService;

    private final VideoPathRepository videoPathRepository;

    public VideoPathController(VideoPathRepository videoPathRepository) {
        this.videoPathRepository = videoPathRepository;
    }

    @GetMapping("/byAtId/{animateId}")
    public List<VideoPath> getVideoPathByAnimateId(@PathVariable Integer animateId) {
     return videoPathRepository.getAllByAnimateId(animateId);
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

    @PostMapping("/responseTask/{id}")
    public Boolean responseTask(@PathVariable Integer id, @RequestBody Task task) {
        return videoPathService.responseTask(id,task);
    }

}
