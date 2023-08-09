package io.github.franzli347.darach.repository;

import io.github.franzli347.darach.model.entity.VideoPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoPathRepository extends JpaRepository<VideoPath, Integer> {

    List<VideoPath> getAllByAnimateId(Integer animateId);

    VideoPath getAllByAnimateIdAndEpoNum(Integer animateId,Integer epoNum);
}
