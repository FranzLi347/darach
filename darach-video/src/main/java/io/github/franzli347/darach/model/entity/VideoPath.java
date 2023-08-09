package io.github.franzli347.darach.model.entity;

import cn.zhxu.bs.bean.SearchBean;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @Column(name = "epo_num")
    private Integer epoNum;

    @Column(name="url")
    private String url;

    @Column(name = "update_time")
    @LastModifiedDate
    private LocalDateTime updateTime;

    @Column(name = "insert_time")
    @CreatedDate
    private LocalDateTime insertTime;

    @Size(max = 255)
    @Column(name = "is_delete")
    private String isDelete;

}