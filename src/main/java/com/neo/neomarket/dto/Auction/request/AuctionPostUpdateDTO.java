package com.neo.neomarket.dto.Auction.request;


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
    private String deadline;
    private String category;

}
