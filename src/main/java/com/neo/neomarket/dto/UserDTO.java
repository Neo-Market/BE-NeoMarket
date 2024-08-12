package com.neo.neomarket.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String nickname;

    private String username;

    private String email;

    private String address;

    private String accountNumber;

    private String bankName;

    private Long point;
}
