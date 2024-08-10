package com.neo.neomarket.entity.elasticsearch;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Getter
@Document(indexName = "user_log")
public class UserLogEntity {
    @Id
    private Long id;

    private Long userId;

    private Long exchangeAmount;

    private String payType;

    private String payStatus;

    private LocalDateTime logTime;
}
