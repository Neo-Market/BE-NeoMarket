package com.neo.neomarket.entity.elasticsearch;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;


@Getter
@Document(indexName = "auction_log")
public class AuctionLogEntity {
    @Id
    private Long id;

    private Long bidPrice;

    private LocalDateTime bidTime;
}