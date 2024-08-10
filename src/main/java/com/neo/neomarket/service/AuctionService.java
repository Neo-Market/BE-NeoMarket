package com.neo.neomarket.service;

import com.neo.neomarket.dto.BidLogDTO;

public interface AuctionService {
    void recordBidLog(BidLogDTO bidLogDTO);
}
