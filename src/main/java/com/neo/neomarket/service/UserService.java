
package com.neo.neomarket.service;

import com.neo.neomarket.dto.user.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.user.UserExchangeLogDTO;
import com.neo.neomarket.dto.user.UserSaveDTO;
import com.neo.neomarket.dto.user.UserInfoDTO;
import com.neo.neomarket.dto.WishDTO;
import java.util.List;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    Long createUser(OAuth2User principal, UserSaveDTO userSaveDTO);
    UserInfoDTO getCurrentUserInfo(OAuth2User principal);
    UserInfoDTO getUserInfo(OAuth2User principal, Long id);
    void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO);
    List<WishDTO> findWishAll(Long id);
    void chargeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO);
    void exchangeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO);
}