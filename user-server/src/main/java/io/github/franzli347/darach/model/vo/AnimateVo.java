package io.github.franzli347.darach.model.vo;

import cn.zhxu.bs.bean.DbField;
import cn.zhxu.bs.bean.SearchBean;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SearchBean(
        tables = "animate"
)
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
     * 剧集数
     */
    @DbField("select count(*) from vedio_path where animate_id = animate.id")
    private Integer episodeNum;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
