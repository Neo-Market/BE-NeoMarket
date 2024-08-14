package com.neo.neomarket.controller;

import com.neo.neomarket.dto.Auction.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.Auction.request.response.AuctionPostDTO;
import com.neo.neomarket.dto.Auction.request.response.AuctionPostReadDTO;
import com.neo.neomarket.dto.Auction.request.AuctionPostUpdateDTO;
import com.neo.neomarket.service.AuctionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Auction API", description = "Auction Controller")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {

    private final AuctionService auctionService;

    // list read
    @GetMapping("/auction/list")
    public ResponseEntity<List<AuctionPostDTO>> getAuctionList() {

        List<AuctionPostDTO> auctionList = auctionService.getAuctionPosts(); // 서비스 호출
        return ResponseEntity.ok(auctionList); // 결과를 ResponseEntity로 반환
    }

    // post read
    @GetMapping("/auction/{id}")
    public ResponseEntity<AuctionPostReadDTO> getAuctionById(@PathVariable Long id) {
        AuctionPostReadDTO auctionPost = auctionService.getAuctionPostById(id);
        return ResponseEntity.ok(auctionPost);
    }

    //create
    @PostMapping("/auction")
    public ResponseEntity<AuctionPostCreateDTO> createAuction(@RequestBody AuctionPostCreateDTO auctionPostCreateDTO) {
        AuctionPostCreateDTO createdPost = auctionService.createAuctionPost(auctionPostCreateDTO);
        return ResponseEntity.ok().body(createdPost);
    }
    //update
    @PatchMapping("/auction/{id}")
    public ResponseEntity<AuctionPostUpdateDTO> updateAuction(@PathVariable Long id, @RequestBody AuctionPostUpdateDTO auctionPostUpdateDTO) {
        AuctionPostUpdateDTO updatedPost = auctionService.updateAuctionPost(id, auctionPostUpdateDTO);
        return ResponseEntity.ok(updatedPost);
    }

    //delete
    @DeleteMapping("/auction/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuctionPost(id);
        return ResponseEntity.noContent().build(); //성공적으로 삭제된 경우 204 No content 응답한다.
    }
}
