package io.github.franzli347.darach.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 
 * @TableName animate
 */
@Entity(name = "animate")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    private LocalDateTime updateTime;

    private LocalDateTime insertTime;

    /**
     * 是否删除
     */
    private String isDelete;


    private static final long serialVersionUID = 1L;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Animate animate = (Animate) o;
        return getId() != null && Objects.equals(getId(), animate.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}