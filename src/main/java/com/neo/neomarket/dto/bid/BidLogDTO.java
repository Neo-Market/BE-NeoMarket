package com.neo.neomarket.dto.bid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidLogDTO {

    private Long userId;
    private Long postId;
    private Long bidAmount;
    private String category;

}
