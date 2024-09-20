package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.elasticsearch.AuctionLogEntity;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionPostRepository extends JpaRepository<AuctionPostEntity, Long> {
    List<AuctionPostEntity> findAllByOrderByLastModifiedDateDesc();

}
