package io.github.franzli347.darach.model.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class AnimateCreateDto {

    /**
     * 动漫名
     */
    private String name;

    /**
     * 展示图片url
     */
    private String displayImgUrl;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 动漫类型
     */
    private String animateType;

    /**
     * 其它名称
     */
    private String otherName;

    /**
     * 制作公司
     */
    private String companyName;

    /**
     * 首播时间
     */
    private LocalDateTime premiereTime;

    /**
     * 更新时间
     */
    private LocalDateTime renewalTime;

    /**
     * 播放地址
     */
    private List<EpisodeDto> episodeList;

}
