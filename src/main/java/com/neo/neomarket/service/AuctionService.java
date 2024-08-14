package com.neo.neomarket.service;

import com.neo.neomarket.dto.BidLogDTO;
import com.neo.neomarket.dto.BidRequestDTO;

public interface AuctionService {
    void recordBidLog(BidLogDTO bidLogDTO);
    void bidAction(BidRequestDTO bidRequestDTO);
    void bidSuccessAction(Long postId);
}
