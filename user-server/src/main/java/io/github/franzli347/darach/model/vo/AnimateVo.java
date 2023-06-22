package io.github.franzli347.darach.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class AnimateVo {
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
     * 作者名称
     */
    private String authorName;

    /**
     * 动漫类型
     */
    private String animateType;

    /**
     * 展示图片路径
     */
    private String displayImgUrl;

    /**
     * 其它名称
     */
    private String otherName;

    /**
     * 制作公司
     */
    private Integer companyName;

    /**
     * 首播时间
     */
    private Date premiereTime;

    /**
     * 更新时间
     */
    private Date renewalTime;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
