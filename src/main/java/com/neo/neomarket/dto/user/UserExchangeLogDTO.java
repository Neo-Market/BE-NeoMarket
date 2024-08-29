package com.neo.neomarket.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExchangeLogDTO {
    private Long userId;

    private Long exchangeAmount;

    private String payType;

    private String payStatus;
}
