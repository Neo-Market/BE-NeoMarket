package com.neo.neomarket.dto.Auction.request;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuctionPostCreateDTO {
    private String title;
    private List<String> pictureUrls;
    private String content;
    private Long startPrice;
    private Long currentPrice;
    private LocalDateTime deadline;
    private String category;
    private Long userId;

}