package com.neo.neomarket.controller;

import com.neo.neomarket.dto.AuctionPostDTO;
import com.neo.neomarket.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {

    private final AuctionService auctionService;
    @GetMapping("/api/auction/list")
    public ResponseEntity<List<AuctionPostDTO>> getAuctionList() {

        List<AuctionPostDTO> auctionList = auctionService.getAuctionPosts(); // 서비스 호출
        return ResponseEntity.ok(auctionList); // 결과를 ResponseEntity로 반환
    }

    @GetMapping("/api/auction")
    public ResponseEntity<AuctionPostDTO> getAuction(@RequestParam int auctionId) {
        AuctionPostDTO auctionPost = auctionService.getAuctionPostById(auctionId);
        return ResponseEntity.ok(auctionPost);
    }
}
