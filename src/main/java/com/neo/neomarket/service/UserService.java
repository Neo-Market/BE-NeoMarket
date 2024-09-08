
package com.neo.neomarket.service;

import com.neo.neomarket.dto.wish.PostShowDTO;
import com.neo.neomarket.dto.user.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.user.UserExchangeLogDTO;
import com.neo.neomarket.dto.user.UserInfoDTO;
import com.neo.neomarket.dto.user.UserSaveDTO;
import java.util.List;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    Long createUser(OAuth2User oAuth2User, UserSaveDTO userSaveDTO);

    UserInfoDTO getUserInfo(OAuth2User oAuth2User);

    UserInfoDTO getUserInfoByEmail(String email);

    void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO);

    List<PostShowDTO> findAllWish(Long id);

    void chargeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO);

    void exchangeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO);

}