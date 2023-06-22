package io.github.franzli347.darach.model.dto;

import lombok.Data;

@Data
public class EpisodeDto {

    /**
     * 集数
     */
    private String id;

    /**
     * m3u8文件路径
     */
    private String masterFileRPath;

}

