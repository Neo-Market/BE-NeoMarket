package com.neo.neomarket.dto.user;

import com.neo.neomarket.entity.mysql.user.Role;
import com.neo.neomarket.entity.mysql.user.UserEntity;
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

    public UserEntity toEntity(OAuth2User oAuth2User) {

        return UserEntity.builder()
                .name(oAuth2User.getAttribute("name"))
                .email(oAuth2User.getAttribute("email"))
                .picture(oAuth2User.getAttribute("picture"))
                .nickname(this.nickname)
                .address(this.address)
                .accountNumber(this.accountNumber)
                .bankName(this.bankName)
                .role(Role.USER)
                .point(0L)
                .build();
    }
}