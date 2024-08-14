package com.neo.neomarket.controller;

import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Wishlist API", description = "Wishlist Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishService;

    // 위시리스트에 추가
    @PostMapping("/{userId}/auction/{auctionPostId}")
    public ResponseEntity<Void> addToWishlist(@PathVariable Long userId, @PathVariable Long auctionPostId) {
        wishService.addToWishlist(userId, auctionPostId);
        return ResponseEntity.ok().build();
    }

    // 위시리스트에서 삭제
    @DeleteMapping("/{userId}/auction/{auctionPostId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long userId, @PathVariable Long auctionPostId) {
        wishService.removeFromWishlist(userId, auctionPostId);
        return ResponseEntity.noContent().build();
    }


}
