package com.neo.neomarket.entity.elasticsearch;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Getter
@Document(indexName = "user_log")
public class UserLogEntity {
    @Id
    private String id;

    @Field(name = "pay_type", type = FieldType.Keyword)
    private String payType;

    @Field(name = "exchange_amount", type = FieldType.Long)
    private Long exchangeAmount;

    @Field(name = "pay_status", type = FieldType.Keyword)
    private String payStatus;

    @Field(type = FieldType.Date, name = "@timestamp")
    private Instant timestamp;

    @Field(name = "user_id", type = FieldType.Long)
    private Long userId;
}
