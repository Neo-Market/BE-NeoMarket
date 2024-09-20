package com.neo.neomarket.controller;

import com.neo.neomarket.dto.usedPost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedPost.UsedPostShowDTO;
import com.neo.neomarket.dto.usedPost.UsedPostDetailDTO;
import com.neo.neomarket.dto.usedPost.UsedPostUpdateDTO;
import com.neo.neomarket.service.UsedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UsedPostController {

    private final UsedPostService usedPostService;

    @GetMapping("/used/list")
    public ResponseEntity<List<UsedPostShowDTO>> getUsedPostList() {
        List<UsedPostShowDTO> usedPostShowDTOList = usedPostService.getUsedPosts();
        return ResponseEntity.ok(usedPostShowDTOList);
    }

    @GetMapping("/used/{id}")
    public ResponseEntity<UsedPostDetailDTO> findUsedPostById(@PathVariable Long id) {
        UsedPostDetailDTO usedPostDetailDTO = usedPostService.findUsedPostById(id);
        return ResponseEntity.ok(usedPostDetailDTO);

    }

    @PostMapping("/used")
    public ResponseEntity<Long> createUsedPost(@RequestPart("createDto") UsedPostCreateDTO usedPostCreateDTO,
                                               @RequestPart("file") MultipartFile file) {
        Long createdPost = usedPostService.createUsedPost(usedPostCreateDTO, file);

        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("used/{id}")
    public ResponseEntity<Void> updateUsedPost(@PathVariable Long id,
                                               @RequestBody UsedPostUpdateDTO usedPostUpdateDTO) {
        usedPostService.updateUsedPost(id, usedPostUpdateDTO);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("used/{id}")
    public ResponseEntity<Void> deleteUsedPost(@PathVariable Long id) {
        usedPostService.deleteUsedPost(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}