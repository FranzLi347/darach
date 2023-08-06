package io.github.franzli347.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "bucketName")
    private String bucketName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "task_param")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> taskParam;

    @Column(name = "task_result")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> taskResult;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

}