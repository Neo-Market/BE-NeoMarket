package com.neo.neomarket.controller;

import com.neo.neomarket.dto.user.WishDTO;
import com.neo.neomarket.service.WishService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @PostMapping("/wish")
    public ResponseEntity<Void> addWishPost(@RequestBody WishDTO wishDTO, UriComponentsBuilder uriComponentsBuilder) {
        Long wishId = wishService.addWish(wishDTO);
        URI location = uriComponentsBuilder.path("/wish/{id}")
                .buildAndExpand(wishId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping("/wish/{id}")
    public ResponseEntity<Void> removeWishPost(@PathVariable Long id) {
        wishService.removeWish(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}