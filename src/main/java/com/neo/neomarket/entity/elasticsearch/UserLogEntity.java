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
    private Long id;

    @Field(type = FieldType.Long)
    private Long bidAmount;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Date, name = "@timestamp")
    private Instant timestamp;

    @Field(type = FieldType.Long)
    private Long userId;
}
