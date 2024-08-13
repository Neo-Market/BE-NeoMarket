package com.neo.neomarket.service;

import com.neo.neomarket.dto.*;

import java.util.List;

public interface UserService {
    void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO);
    List<WishDTO> findWishAll(Long id);
    UserDTO userInfo(Long id);
    void chargeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO);
    void exchangeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO);
}
