package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<WishEntity, Long> {
}
