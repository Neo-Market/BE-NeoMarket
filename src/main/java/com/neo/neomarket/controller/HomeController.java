package com.neo.neomarket.controller;

import com.neo.neomarket.dto.home.RecentPostShowDTO;
import com.neo.neomarket.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/home")
    ResponseEntity<List<RecentPostShowDTO>> getRecentPosts(){
        List<RecentPostShowDTO> posts = homeService.getRecentPosts();
        return ResponseEntity.ok(posts);
    }

}
