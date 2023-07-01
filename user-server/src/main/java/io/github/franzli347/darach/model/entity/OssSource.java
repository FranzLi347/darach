package io.github.franzli347.darach.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName oss_source
 */
@TableName(value ="oss_source")
@Data
public class OssSource implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * oss类型
     */
    private String type;

    /**
     * oss url
     */
    private String url;

    /**
     * 
     */
    private String accessKey;

    /**
     * 
     */
    private String secertKey;

    private String bucketName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}