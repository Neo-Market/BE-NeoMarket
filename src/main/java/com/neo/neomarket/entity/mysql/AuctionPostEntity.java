package com.neo.neomarket.entity.mysql;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private String status;

    private Long startPrice;

    private Long currentPrice;

    @Builder.Default
    @Column(nullable = false)
    private Long views = 0L;

    @Column(nullable = false)
    private LocalDateTime deadline;


    private Boolean deleted;


    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "auctionPost")
    private final List<WishEntity> wishes =new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "auctionPost")
    private final List<PictureEntity> pictures = new ArrayList<>();

}

