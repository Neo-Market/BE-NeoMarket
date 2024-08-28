package com.neo.neomarket.dto.Auction.request.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuctionPostReadDTO {

    private Long id;
    private String title;
    private List<String> pictureUrls;
    private String content;
    private LocalDateTime deadline;
    private String category;
    private Long startPrice;
    private Long currentPrice;
    private String nickname;

}