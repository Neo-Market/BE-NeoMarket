package com.neo.neomarket.service;

import com.neo.neomarket.dto.auctionPost.AuctionPostCreateDTO;
import com.neo.neomarket.dto.auctionPost.AuctionPostShowDTO;
import com.neo.neomarket.dto.auctionPost.AuctionPostDetailDTO;
import com.neo.neomarket.dto.bid.BidLogDTO;
import com.neo.neomarket.dto.bid.BidRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuctionService {
    List<AuctionPostShowDTO> getAuctionPosts();

    AuctionPostDetailDTO getAuctionPostById(Long id);

    Long createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO, MultipartFile file);

    void deleteAuctionPost(Long id);

    void bidAction(BidRequestDTO bidRequestDTO);

    void bidSuccessAction(Long postId);

    void recordBidLog(BidLogDTO bidLogDTO);
}