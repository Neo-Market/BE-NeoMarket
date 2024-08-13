package com.neo.neomarket.dto;


import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuctionPostDTO {
    private String title;
    //private String pictureUrl;
    private List<String> pictureUrls;
    private String content;
    private Long startPrice;
    private Long currentPrice;
    private String deadline;
    private String category;
    private int status;
    private Long userId;

}
