package com.neo.neomarket.entity.elasticsearch;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;


@Getter
@Document(indexName = "auction_log")
public class AuctionLogEntity {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String payType;

    @Field(type = FieldType.Long)
    private Long exchangeAmount;

    @Field(type = FieldType.Keyword)
    private String payStatus;

    @Field(type = FieldType.Long)
    private Long userId;

    @Field(type = FieldType.Date, name = "@timestamp")
    private Instant timestamp;
}