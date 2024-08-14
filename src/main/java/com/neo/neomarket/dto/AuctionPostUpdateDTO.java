package com.neo.neomarket.dto;


import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuctionPostUpdateDTO {
    private String title;
    private List<String> pictureUrls;
    private String content;
    private Long startPrice;
    private Long currentPrice;
    private String deadline;
    private String category;
    private Long userId;

}
