package io.github.franzli347.repository;

import io.github.franzli347.model.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, Integer> {
}