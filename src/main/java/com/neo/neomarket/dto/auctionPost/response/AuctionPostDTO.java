package com.neo.neomarket.dto.auctionPost.response;



import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuctionPostDTO {

    private Long id;
    private String title;
    private String picture;
    private String content;
    private Long startPrice;
    private Long currentPrice;
    private LocalDateTime deadline;
    private String category;
    private int status;
    private Long userId;

}