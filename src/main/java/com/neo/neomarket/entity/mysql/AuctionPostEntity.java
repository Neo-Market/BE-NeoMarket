package com.neo.neomarket.entity.mysql;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name="AUCTION_POST")
public class AuctionPostEntity  extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int status = 0;

    // 상태를 상수로 정의
    public static final int STATUS_ACTIVE = 0; // 활성 상태
    public static final int STATUS_INACTIVE = 1; // 비활성 상태
    public static final int STATUS_CLOSED = 2; // 종료 상태

    private Long startPrice;

    private Long currentPrice;

    private Long views;

    @Column(nullable = false)
    private String deadline;

    private Boolean deleted;

    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "auctionPost")
    private final List<WishEntity> wishes =new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "auctionPost")
    private final List<PictureEntity> pictures = new ArrayList<>();
}

