package com.neo.neomarket.controller;

import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.entity.mysql.WishEntity;
import com.neo.neomarket.service.WishService;
import com.neo.neomarket.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WishController {

    @Autowired
    private WishService wishService;

    @PostMapping("/auction/{id}/wish")
    public ResponseEntity<String> addToWishAuction(@PathVariable Long id, @RequestBody WishDTO wishDTO) {
        wishDTO.setWishId(id);
        wishDTO.setPostType(0L);
        wishService.addToWish(wishDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added to wishlist");
    }

    @PostMapping("/used/{id}/wish")
    public ResponseEntity<String> addToWishUsed(@PathVariable Long id, @RequestBody WishDTO wishDTO) {
        wishDTO.setWishId(id);
        wishDTO.setPostType(1L);
        wishService.addToWish(wishDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added to wishlist");
    }

    @DeleteMapping("/auction/{id}/wish")
    public ResponseEntity<String> removeFromWishAuction(@PathVariable Long id, @RequestBody WishDTO wishDTO) {
        wishDTO.setWishId(id);
        wishDTO.setPostType(0L);
        wishService.removeFromWish(wishDTO);
        return ResponseEntity.ok("Removed from wishlist");
    }

    @DeleteMapping("/used/{id}/wish")
    public ResponseEntity<String> removeFromWishUsed(@PathVariable Long id, @RequestBody WishDTO wishDTO) {
        wishDTO.setWishId(id);
        wishDTO.setPostType(1L);
        wishService.removeFromWish(wishDTO);
        return ResponseEntity.ok("Removed from wishlist");
    }

    @GetMapping("/wish/{userId}/search")
    public ResponseEntity<List<WishDTO>> findWishByTitle(@PathVariable Long userId, @RequestParam Long title) {
        List<WishDTO> wishes = wishService.findWishByTitle(userId, title);
        if (wishes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(wishes);
    }
}
