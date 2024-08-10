package com.neo.neomarket.service;

import com.neo.neomarket.dto.UserExchangeLogDTO;

public interface UserService {
    void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO);
}
