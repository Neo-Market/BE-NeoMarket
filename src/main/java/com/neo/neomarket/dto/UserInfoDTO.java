package com.neo.neomarket.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO {

    private String name;

    private String email;

    private String picture;

    private String nickname;

    private String address;

    private String accountNumber;

    private String bankName;

    private Long point;
}
