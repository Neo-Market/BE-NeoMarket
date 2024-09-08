package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface AuctionPostRepository extends JpaRepository<AuctionPostEntity, Long> {
    List<AuctionPostEntity> findAllByOrderByLastModifiedDateDesc();

    List<AuctionPostEntity> findTop4ByOrderByLastModifiedDateDesc();
}
