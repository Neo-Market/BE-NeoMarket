package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.BidLogDTO;
import com.neo.neomarket.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuctionServiceImpl implements AuctionService {


    @Override
    public void recordBidLog(BidLogDTO bidLogDTO){
        Logger logger = LoggerFactory.getLogger("AuctionServiceLogger");
        logger.info("{}", bidLogDTO);
    }

}
