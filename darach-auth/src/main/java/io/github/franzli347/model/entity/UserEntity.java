package io.github.franzli347.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @TableName user
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "user")
public class UserEntity implements Serializable {
    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 0:正常 1:待审核 2:封禁
     */
    private String status;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String idDelete;


    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JoinTable(
            name="user_role_map", // 关联表名
            joinColumns=@JoinColumn(name="user_id"), // 当前实体在关联表的外键
            inverseJoinColumns=@JoinColumn(name="role_id") // 关联实体在关联表的外键
    )
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private List<RoleEntity> roleEntities;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserEntity userEntity = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), userEntity.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}