package io.github.franzli347.darach.service;


import io.github.franzli347.darach.model.dto.CoverTaskCreateDto;
import io.github.franzli347.darach.model.entity.VideoPath;

import java.util.List;

/**
* @author franz
*/
public interface VideoPathService {


    Boolean saveVideoPath(VideoPath videoPath);

    Boolean updateVideoPath(VideoPath videoPath);

    VideoPath getVideoPathById(Integer id);

    Boolean createVideoCoverTask(CoverTaskCreateDto videoPath);

    List<Integer> getEpoNumsByAnimateId(Integer animateId);

    String getVideoPathByAnimateIdAndEpoNum(Integer animateId, Integer epoNum);
}
