package io.github.franzli347.darach.model.entity;

import cn.zhxu.bs.bean.SearchBean;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_path")
@SearchBean
public class VideoPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "animate_id")
    private Integer animateId;

    @Column(name="url")
    private String url;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

    @Size(max = 255)
    @Column(name = "is_delete")
    private String isDelete;

}