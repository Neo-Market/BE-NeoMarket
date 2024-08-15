package com.neo.neomarket.entity.mysql;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="AUCTION_POST_BACKUP")
public class AuctionPostBackupEntity  extends BaseTimeEntity {
    @Id
    private Long id;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private Long startPrice;

    private Long currentPrice;

    @Column(nullable = false)
    private Long views;

    @Column(nullable = false)
    private LocalDateTime deadline;

    private String category;

    @Column(name ="user_id")
    private Long userId;

    @Column(name = "deleted_at", nullable = false, updatable = false)
    private LocalDateTime deletedAt;
}
