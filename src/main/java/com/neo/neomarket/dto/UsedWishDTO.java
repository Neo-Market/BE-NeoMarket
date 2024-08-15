package com.neo.neomarket.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsedWishDTO {
    private Long userId;
    private Long postId;
}