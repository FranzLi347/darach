package io.github.franzli347.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @ManyToMany
    @JoinTable(
            name="role_permission_map", // 关联表名
            joinColumns=@JoinColumn(name="role_id"), // 当前实体在关联表的外键
            inverseJoinColumns=@JoinColumn(name="permission_id") // 关联实体在关联表的外键
    )
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private List<PermissionEntity> permissionEntities;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RoleEntity roleEntity = (RoleEntity) o;
        return getId() != null && Objects.equals(getId(), roleEntity.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}