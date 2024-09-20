package com.neo.neomarket.controller;

import com.neo.neomarket.dto.wish.PostShowDTO;
import com.neo.neomarket.dto.wish.WishDTO;
import com.neo.neomarket.service.WishService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/wish")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @GetMapping("/{id}")
    public ResponseEntity<PostShowDTO> showWishPost(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(wishService.showWish(id));
    }

    @PostMapping
    public ResponseEntity<Void> addWishPost(@RequestBody WishDTO wishDTO, UriComponentsBuilder uriComponentsBuilder) {
        Long wishId = wishService.addWish(wishDTO);
        URI location = uriComponentsBuilder.path("/wish/{id}")
                .buildAndExpand(wishId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeWishPost(@PathVariable(name = "id") Long id) {
        wishService.removeWish(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}