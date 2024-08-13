package com.neo.neomarket.controller;

import com.neo.neomarket.dto.AuctionPostCreateDTO;
import com.neo.neomarket.dto.AuctionPostDTO;
import com.neo.neomarket.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<AuctionPostDTO> getAuctionById(@PathVariable Long id) {
        AuctionPostDTO auctionPost = auctionService.getAuctionPostById(id);
        return ResponseEntity.ok(auctionPost);
    }

    //create
    @PostMapping("/auction")
    public ResponseEntity<AuctionPostCreateDTO> createAuction(@RequestBody AuctionPostCreateDTO auctionPostCreateDTO) {
        AuctionPostCreateDTO createdPost = auctionService.createAuctionPost(auctionPostCreateDTO);
        return ResponseEntity.ok().body(createdPost);
    }
    //update
    @PutMapping("/auction/{id}")
    public ResponseEntity<AuctionPostDTO> updateAuction(@PathVariable Long id, @RequestBody AuctionPostDTO auctionPostDTO) {
        AuctionPostDTO updatedPost = auctionService.updateAuctionPost(id, auctionPostDTO);
        return ResponseEntity.ok(updatedPost);
    }

    //delete
    @DeleteMapping("/auction/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuctionPost(id);
        return ResponseEntity.noContent().build(); //성공적으로 삭제된 경우 204 No content 응답한다.
    }
}
