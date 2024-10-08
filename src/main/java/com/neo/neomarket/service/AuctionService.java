package com.neo.neomarket.service;

import com.neo.neomarket.dto.auctionPost.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.auctionPost.request.AuctionPostUpdateDTO;

import com.neo.neomarket.dto.auctionPost.response.AuctionPostDTO;
import com.neo.neomarket.dto.auctionPost.response.AuctionPostReadDTO;
import com.neo.neomarket.dto.bid.BidLogDTO;
import com.neo.neomarket.dto.bid.BidRequestDTO;
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