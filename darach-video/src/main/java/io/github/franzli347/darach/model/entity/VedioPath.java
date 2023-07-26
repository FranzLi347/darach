package io.github.franzli347.darach.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName vedio_path
 */
@Entity(name = "vedio_path")
@Data
public class VedioPath implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * animate id
     */
    private Integer animateId;

    private String episode;

    private String fileName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 是否删除
     */
    private String isDelete;


    private static final long serialVersionUID = 1L;
}