package com.neo.neomarket.dto.auctionPost;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuctionPostCreateDTO {

    private String title;
    private String content;
    private Long startPrice;
    private Long currentPrice;
    private LocalDateTime deadline;
    private String category;
    private String picture;
    private Long userId;

}