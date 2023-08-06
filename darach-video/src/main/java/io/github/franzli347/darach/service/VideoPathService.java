package io.github.franzli347.darach.service;


import io.github.franzli347.darach.model.dto.CoverTaskCreateDto;
import io.github.franzli347.darach.model.entity.VideoPath;
import io.github.franzli347.model.entity.Task;

import java.util.List;

/**
* @author franz
*/
public interface VideoPathService {

    List<VideoPath> getVideoPathByAnimateId(Integer animateId);

    Boolean saveVideoPath(VideoPath videoPath);

    Boolean updateVideoPath(VideoPath videoPath);

    VideoPath getVideoPathById(Integer id);

    Boolean createVideoCoverTask(CoverTaskCreateDto videoPath);

    Boolean responseTask(Integer id, Task task);
}
