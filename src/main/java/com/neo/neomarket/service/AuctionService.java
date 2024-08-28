package com.neo.neomarket.service;

import com.neo.neomarket.dto.Auction.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.Auction.request.AuctionPostUpdateDTO;

import com.neo.neomarket.dto.Auction.request.response.AuctionPostDTO;
import com.neo.neomarket.dto.Auction.request.response.AuctionPostReadDTO;
import com.neo.neomarket.dto.BidLogDTO;
import com.neo.neomarket.dto.BidRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuctionService {
    void recordBidLog(BidLogDTO bidLogDTO);
    List<AuctionPostDTO> getAuctionPosts();
    AuctionPostReadDTO getAuctionPostById(Long id);
    void updateAuctionPost(Long id, AuctionPostUpdateDTO auctionPostUpdateDTO);
    void deleteAuctionPost(Long id);
    Long createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO, List<MultipartFile> pictures);
    void bidAction(BidRequestDTO bidRequestDTO);
    void bidSuccessAction(Long postId);
}