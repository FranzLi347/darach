package io.github.franzli347.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "permission", schema = "darach", catalog = "")
public class PermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "permission", nullable = true, length = 255)
    private String permission;
    @Basic
    @Column(name = "update_time", nullable = true)
    private LocalDateTime updateTime;
    @Basic
    @Column(name = "insert_time", nullable = true)
    private LocalDateTime insertTime;
    @Basic
    @Column(name = "is_delete", nullable = true)
    private Byte isDelete;

}
