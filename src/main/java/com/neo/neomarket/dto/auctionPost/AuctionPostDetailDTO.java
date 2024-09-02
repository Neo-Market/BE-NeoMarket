package com.neo.neomarket.dto.auctionPost;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuctionPostDetailDTO {

    private String title;
    private String content;
    private List<String> pictures;
    private LocalDateTime deadline;
    private String category;
    private Long startPrice;
    private Long currentPrice;
    private String nickname;
    private Long views;

}