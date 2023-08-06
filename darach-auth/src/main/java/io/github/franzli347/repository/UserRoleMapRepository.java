package io.github.franzli347.repository;

import io.github.franzli347.model.entity.UserRoleMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, Integer> {
}