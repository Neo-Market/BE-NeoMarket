package com.neo.neomarket.controller;


import com.neo.neomarket.dto.Auction.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.response.AuctionPostDTO;
import com.neo.neomarket.dto.response.AuctionPostReadDTO;
import com.neo.neomarket.dto.Auction.request.AuctionPostUpdateDTO;
import com.neo.neomarket.service.AuctionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Auction API", description = "Auction Controller")
>>>>>>> 4ba35d47c883ff8a6d62c886bebba9526962525f

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {

    private final AuctionService auctionService;


    // list read
    @GetMapping("/auction/list")
    public ResponseEntity<List<AuctionPostDTO>> getAuctionList() {
>>>>>>> 4ba35d47c883ff8a6d62c886bebba9526962525f
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
    public ResponseEntity<Long> createAuction(

            @RequestParam("auctionPost") AuctionPostCreateDTO auctionPostCreateDTO,
            @RequestParam("pictures") List<MultipartFile> pictures) {
        Long createdPost = auctionService.createAuctionPost(auctionPostCreateDTO, pictures);
        return ResponseEntity.ok().body(createdPost);
    }
    //update
    @PutMapping("/auction/{id}")
    public ResponseEntity<Void> updateAuction(@PathVariable Long id, @RequestBody AuctionPostUpdateDTO auctionPostUpdateDTO) {
        auctionService.updateAuctionPost(id, auctionPostUpdateDTO);
        return ResponseEntity.ok().build();
    }

    //delete
    @DeleteMapping("/auction/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuctionPost(id);
        return ResponseEntity.noContent().build(); //성공적으로 삭제된 경우 204 No content 응답한다.
    }

>>>>>>> 4ba35d47c883ff8a6d62c886bebba9526962525f
}
