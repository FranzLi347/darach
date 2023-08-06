package io.github.franzli347.repository;

import io.github.franzli347.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email);

}
