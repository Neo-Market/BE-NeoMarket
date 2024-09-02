package com.neo.neomarket.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO {

    @Schema(description = "유저 아이디")
    private Long id;

    @Schema(description = "유저 이름")
    private String name;

    @Schema(description = "유저 이메일")
    private String email;

    @Schema(description = "유저 프로필 사진")
    private String picture;

    @Schema(description = "유저 닉네임")
    private String nickname;

    @Schema(description = "유저 주소")
    private String address;

    @Schema(description = "유저 계좌번호")
    private String accountNumber;

    @Schema(description = "유저 은행 이름")
    private String bankName;

    @Schema(description = "유저 네오 포인트")
    private Long point;

}