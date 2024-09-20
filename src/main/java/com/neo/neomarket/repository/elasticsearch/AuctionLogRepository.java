package com.neo.neomarket.repository.elasticsearch;

import com.neo.neomarket.entity.elasticsearch.AuctionLogEntity;
import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionLogRepository extends ElasticsearchRepository<AuctionLogEntity, Long> {
    List<AuctionLogEntity> findByPostIdOrderByBidAmountDesc(Long postId);

    Optional<AuctionLogEntity> findTopByPostIdOrderByBidAmountDesc(Long postId);
}
