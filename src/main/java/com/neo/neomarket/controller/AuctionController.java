package com.neo.neomarket.controller;

import com.neo.neomarket.dto.BidRequestDTO;
import com.neo.neomarket.service.AuctionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auction API", description = "Auction Controller")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {
    private final AuctionService auctionService;
    @PostMapping("/auction/bid")
    ResponseEntity<Void> bidAuction(@RequestBody BidRequestDTO bidRequestDTO){
        auctionService.bidAction(bidRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auction/bid/success")
    ResponseEntity<Void> bidAuctionSuccess(@RequestParam(name = "id")Long postId){
        auctionService.bidSuccessAction(postId);
        return ResponseEntity.ok().build();
    }
}
