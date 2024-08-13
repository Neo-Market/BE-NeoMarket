package com.neo.neomarket.controller;

import com.neo.neomarket.dto.UsedPostDTO;
import com.neo.neomarket.service.UsedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UsedPostController {

    private final UsedPostService usedPostService;

    // 전체 게시글 조회 엔드포인트
    @GetMapping("/used/list")
    public ResponseEntity<List<UsedPostDTO>> getUsedList() {
       List<UsedPostDTO> postList = usedPostService.getUsedPosts();

        return ResponseEntity.ok().body(postList);
    }

    // ID로 게시글 조회
    @GetMapping("/used/{id}")
    public ResponseEntity<UsedPostDTO> findPostById(@PathVariable(name = "id") Long id) {
        UsedPostDTO findPost = usedPostService.findPostById(id);

        return ResponseEntity.ok().body(findPost);
    }

    // 게시글 생성
    @PostMapping("/used")
    public ResponseEntity<UsedPostDTO> createPost(@RequestBody UsedPostDTO usedPostDTO ) {
        UsedPostDTO createdPost = usedPostService.createPost(usedPostDTO);

        return ResponseEntity.ok().body(createdPost);
    }

    // 게시글 수정
    @PatchMapping("used/{id}")
    public ResponseEntity<UsedPostDTO> updatePost(@PathVariable Long id, @RequestBody UsedPostDTO usedPostDTO) {
        UsedPostDTO updatedPost = usedPostService.updatePost(id, usedPostDTO);
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 게시글 삭제
    @DeleteMapping("used/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "id") Long id) {
        usedPostService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    // 게시글을 wish에 추가하는 엔드포인트
//    @PostMapping("used/{id}/wish")
//    public ResponseEntity<Void> addWishToPost(@RequestBody UsedWishDTO usedWishDTO) {
//        try {
//            usedPostService.addWishToPost(usedWishDTO);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // 게시글의 wish를 삭제하는 엔드포인트
//    @DeleteMapping("used/{id}/wish")
//    public ResponseEntity<Void> removeWishFromPost(@RequestBody UsedWishDTO usedWishDTO) {
//        try {
//            usedPostService.removeWishFromPost(usedWishDTO);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
