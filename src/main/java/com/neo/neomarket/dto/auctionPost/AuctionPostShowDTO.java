package com.neo.neomarket.dto.auctionPost;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionPostShowDTO {

    private Long id;
    private String title;
    private String picture;
    private String content;
    private Long startPrice;
    private Long currentPrice;
    private LocalDateTime deadline;
    private String category;
    private String nickname;

}