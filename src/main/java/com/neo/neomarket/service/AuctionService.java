package com.neo.neomarket.service;

import com.neo.neomarket.dto.AuctionPostDTO;
import com.neo.neomarket.dto.BidLogDTO;

import java.util.List;

public interface AuctionService {
    void recordBidLog(BidLogDTO bidLogDTO);
    List<AuctionPostDTO> getAuctionPosts();
    AuctionPostDTO getAuctionPostById(Long id);
    AuctionPostDTO createAuctionPost(AuctionPostDTO auctionPostDTO);
    AuctionPostDTO updateAuctionPost(Long id, AuctionPostDTO auctionPostDTO);
    void deleteAuctionPost(Long id);

}