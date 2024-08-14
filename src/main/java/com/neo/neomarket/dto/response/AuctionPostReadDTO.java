package com.neo.neomarket.dto.response;


import lombok.*;

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
    private String deadline;
    private String category;
    private Long startPrice;
    private Long currentPrice;
    private String nickname;

}
