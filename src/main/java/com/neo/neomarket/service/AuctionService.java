package com.neo.neomarket.service;


import com.neo.neomarket.dto.*;
import com.neo.neomarket.dto.Auction.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.Auction.request.AuctionPostUpdateDTO;
import com.neo.neomarket.dto.response.AuctionPostDTO;
import com.neo.neomarket.dto.response.AuctionPostReadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.List;

public interface AuctionService {
    void recordBidLog(BidLogDTO bidLogDTO);
    List<AuctionPostDTO> getAuctionPosts();
    AuctionPostReadDTO getAuctionPostById(Long id);
    void deleteAuctionPost(Long id);
    Long createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO);
    void bidAction(BidRequestDTO bidRequestDTO);
    void bidSuccessAction(Long postId);
}

