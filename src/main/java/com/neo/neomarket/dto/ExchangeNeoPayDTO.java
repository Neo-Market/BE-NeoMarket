package com.neo.neomarket.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExchangeNeoPayDTO {
    private Long userId;
    private Long point;
}
