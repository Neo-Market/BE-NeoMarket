package com.neo.neomarket.controller;

import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WishController {

    @Autowired
    private WishService wishService;

    @PostMapping("/auction/wish")
    public ResponseEntity<String> addToWishAuction( @RequestBody WishDTO wishDTO) {
        wishService.addToWish(wishDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added to wishlist");
    }

    @PostMapping("/used/wish")
    public ResponseEntity<String> addToWishUsed(@RequestBody WishDTO wishDTO) {
        wishService.addToWish(wishDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added to wishlist");
    }

    @DeleteMapping("/auction/wish")
    public ResponseEntity<String> removeFromWishAuction( @RequestBody WishDTO wishDTO) {
        wishService.removeFromWish(wishDTO);
        return ResponseEntity.ok("Removed from wishlist");
    }

    @DeleteMapping("/used/wish")
    public ResponseEntity<String> removeFromWishUsed( @RequestBody WishDTO wishDTO) {
        wishService.removeFromWish(wishDTO);
        return ResponseEntity.ok("Removed from wishlist");
    }

}
