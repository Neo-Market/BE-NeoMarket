package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.UserExchangeLogDTO;
import com.neo.neomarket.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO){
        Logger logger = LoggerFactory.getLogger("UserServiceLogger");
        logger.info("{}", userExchangeLogDTO);
    }
}
