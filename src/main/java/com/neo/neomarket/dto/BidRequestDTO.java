package com.neo.neomarket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidRequestDTO {
    private Long userId;

    private Long postId;

    private Long bidAmount;
}