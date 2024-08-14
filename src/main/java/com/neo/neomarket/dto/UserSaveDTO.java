package com.neo.neomarket.dto;

import com.neo.neomarket.entity.mysql.Role;
import com.neo.neomarket.entity.mysql.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSaveDTO {

    private String nickname;

    private String address;

    private String accountNumber;

    private String bankName;

    private String role;

    private Long point;

    public UserEntity toEntity(OAuth2User principal) {

        return UserEntity.builder()
                .name(principal.getAttribute("name"))
                .email(principal.getAttribute("email"))
                .picture(principal.getAttribute("picture"))
                .nickname(this.nickname)
                .address(this.address)
                .accountNumber(this.accountNumber)
                .bankName(this.bankName)
                .role(Role.USER)
                .point(0L)
                .build();
    }
}