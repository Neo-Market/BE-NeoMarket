package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.UsedPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedPostRepository extends JpaRepository<UsedPostEntity, Long> {
    List<UsedPostEntity> findAllByOrderByCreatedDateDesc();
}
