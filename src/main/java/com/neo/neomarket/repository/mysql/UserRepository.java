package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByNickname(String nickname);
}
