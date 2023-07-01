package io.github.franzli347.darach.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName vedio_path
 */
@TableName(value ="vedio_path")
@Data
public class VedioPath implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * animate id
     */
    private Integer animateId;

    /**
     * 集数
     */
    private String episode;


    private String fileName;

    /**
     * oss ID
     */
    private Integer ossId;

    /**
     * bucket 名称
     */
    private String bucketName;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}