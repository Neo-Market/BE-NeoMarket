package com.neo.neomarket.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExchangeNeoPayDTO {
    @Schema(description = "유저 id")
    private Long userId;

    @Schema(description = "네오 페이 포인트")
    private Long point;
}
