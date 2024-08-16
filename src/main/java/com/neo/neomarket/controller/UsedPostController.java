package com.neo.neomarket.controller;

import com.neo.neomarket.dto.usedpost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedpost.UsedPostDTO;
import com.neo.neomarket.dto.usedpost.UsedPostIdDTO;
import com.neo.neomarket.dto.usedpost.UsedPostUpdateDTO;
import com.neo.neomarket.exception.CustomException;
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
    public ResponseEntity<UsedPostIdDTO> findPostById(@PathVariable(name = "id") Long id) {
        UsedPostIdDTO findPost = usedPostService.findPostById(id);

        return ResponseEntity.ok().body(findPost);
    }

    // 게시글 생성
    @PostMapping("/used")
    public ResponseEntity<Long> createPost(@RequestBody UsedPostCreateDTO usedPostCreateDTO ) {
        Long createdPost = usedPostService.createPost(usedPostCreateDTO);

        return ResponseEntity.ok().body(createdPost);
    }

    // 게시글 수정
    @PutMapping("used/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody UsedPostUpdateDTO usedPostUpdateDTO) {
        try {
            usedPostService.updatePost(id, usedPostUpdateDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            // 예외 발생 시, 예외에 맞는 상태 코드 반환
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 게시글 삭제
    @DeleteMapping("used/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "id") Long id) {
        usedPostService.deletePost(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
