package com.neo.neomarket.service;


import com.neo.neomarket.dto.*;
import com.neo.neomarket.dto.Auction.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.Auction.request.AuctionPostUpdateDTO;
import com.neo.neomarket.dto.response.AuctionPostDTO;
import com.neo.neomarket.dto.response.AuctionPostReadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
>>>>>>> 4ba35d47c883ff8a6d62c886bebba9526962525f

import java.util.List;

public interface AuctionService {
    void recordBidLog(BidLogDTO bidLogDTO);
    List<AuctionPostDTO> getAuctionPosts();

    AuctionPostReadDTO getAuctionPostById(Long id);
    void updateAuctionPost(Long id, AuctionPostUpdateDTO auctionPostUpdateDTO);
    void deleteAuctionPost(Long id);
    Long createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO, List<MultipartFile> pictures);
>>>>>>> 4ba35d47c883ff8a6d62c886bebba9526962525f
}
