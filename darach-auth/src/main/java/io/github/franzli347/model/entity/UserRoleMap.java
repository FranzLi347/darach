package io.github.franzli347.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user_role_map")
public class UserRoleMap {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "insert_time")
    private Instant insertTime;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "is_delete")
    private Boolean isDelete;

    public UserRoleMap(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRoleMap() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleMap that = (UserRoleMap) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(roleId, that.roleId) && Objects.equals(insertTime, that.insertTime) && Objects.equals(updateTime, that.updateTime) && Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, roleId, insertTime, updateTime, isDelete);
    }
}