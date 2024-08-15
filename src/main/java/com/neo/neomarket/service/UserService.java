
package com.neo.neomarket.service;

import com.neo.neomarket.dto.*;

import com.neo.neomarket.dto.UserSaveDTO;
import com.neo.neomarket.dto.UserInfoDTO;
import java.util.List;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    Long createUser(OAuth2User principal, UserSaveDTO userSaveDTO);
    UserInfoDTO userInfo(OAuth2User principal, Long id);
    void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO);
    List<WishDTO> findWishAll(Long id);
    void chargeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO);
    void exchangeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO);
}
