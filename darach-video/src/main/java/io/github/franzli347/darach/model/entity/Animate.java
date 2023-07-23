package io.github.franzli347.darach.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName animate
 */
@TableName(value ="animate")
@Data
public class Animate implements Serializable {
    /**
     * id
     */
    @TableId
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
    private Date premiereTime;

    /**
     * 更新时间
     */
    private Date renewalTime;

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