package com.neo.neomarket.controller;

import com.neo.neomarket.dto.auctionPost.AuctionPostCreateDTO;
import com.neo.neomarket.dto.auctionPost.AuctionPostShowDTO;
import com.neo.neomarket.dto.auctionPost.AuctionPostDetailDTO;
import com.neo.neomarket.dto.bid.BidRequestDTO;
import com.neo.neomarket.service.AuctionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "Auction API", description = "Auction Controller")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping("/auction/list")
    public ResponseEntity<List<AuctionPostShowDTO>> getAuctionList() {
        List<AuctionPostShowDTO> auctionList = auctionService.getAuctionPosts();
        return ResponseEntity.ok(auctionList);
    }

    @GetMapping("/auction/{id}")
    public ResponseEntity<AuctionPostDetailDTO> getAuctionById(@PathVariable Long id) {
        AuctionPostDetailDTO auctionPost = auctionService.getAuctionPostById(id);
        return ResponseEntity.ok(auctionPost);
    }

    @PostMapping("/auction")
    public ResponseEntity<Long> createAuction(
            @RequestParam("auctionPost") AuctionPostCreateDTO auctionPostCreateDTO,
            @RequestParam("pictures") List<MultipartFile> pictures) {
        Long createdPost = auctionService.createAuctionPost(auctionPostCreateDTO, pictures);
        return ResponseEntity.ok(createdPost);
    }

    @DeleteMapping("/auction/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuctionPost(id);

        return ResponseEntity
                .noContent()
                .build();
    }

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