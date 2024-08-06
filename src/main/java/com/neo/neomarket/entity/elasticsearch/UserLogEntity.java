package com.neo.neomarket.entity.elasticsearch;

import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class UserLogEntity {
    @Id
    private Long id;

    private String payType;

    private String payStatus;

    private LocalDateTime logTime;
}
