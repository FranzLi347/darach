package io.github.franzli347.darach.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @TableName animate
 */
@Entity(name = "animate")
@Data
public class Animate {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 插入时间
     */
    private LocalDateTime insertTime;

    /**
     * 是否删除
     */
    private String isDelete;

    @OneToMany
    @JoinColumn(name = "animate_id")
    @Fetch(FetchMode.JOIN)
    private List<VideoPath> videoPaths;

    
    private static final long serialVersionUID = 1L;
}