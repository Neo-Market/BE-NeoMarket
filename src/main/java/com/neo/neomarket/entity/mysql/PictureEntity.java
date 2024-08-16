package com.neo.neomarket.entity.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "PICTURE")
public class PictureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "auction_post_id") // 오타 수정
    private AuctionPostEntity auctionPost;

    @ManyToOne
    @JoinColumn(name = "used_post_id")
    private UsedPostEntity usedPost;

}
