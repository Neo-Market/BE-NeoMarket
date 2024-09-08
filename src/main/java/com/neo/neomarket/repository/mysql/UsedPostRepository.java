package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.UsedPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface UsedPostRepository extends JpaRepository<UsedPostEntity, Long> {
    List<UsedPostEntity> findAllByOrderByLastModifiedDateDesc();

    List<UsedPostEntity> findTop4ByOrderByLastModifiedDateDesc();
}
