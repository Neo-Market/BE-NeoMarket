package com.neo.neomarket.service;

import com.neo.neomarket.dto.*;
import com.neo.neomarket.dto.Auction.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.Auction.request.AuctionPostUpdateDTO;
import com.neo.neomarket.dto.Auction.request.response.AuctionPostDTO;
import com.neo.neomarket.dto.Auction.request.response.AuctionPostReadDTO;

import java.util.List;

public interface AuctionService {
    void recordBidLog(BidLogDTO bidLogDTO);
    List<AuctionPostDTO> getAuctionPosts();
    AuctionPostReadDTO getAuctionPostById(Long id);
    AuctionPostCreateDTO createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO);
    AuctionPostUpdateDTO updateAuctionPost(Long id, AuctionPostUpdateDTO auctionPostUpdateDTO);
    void deleteAuctionPost(Long id);

}