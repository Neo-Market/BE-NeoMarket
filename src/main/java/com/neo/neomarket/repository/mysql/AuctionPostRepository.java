package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionPostRepository extends JpaRepository<AuctionPostEntity, Long> {
}
