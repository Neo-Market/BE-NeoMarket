package com.neo.neomarket.controller;

import com.neo.neomarket.dto.UserInfoDTO;
import com.neo.neomarket.dto.usedpost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedpost.UsedPostDTO;
import com.neo.neomarket.dto.usedpost.UsedPostIdDTO;
import com.neo.neomarket.dto.usedpost.UsedPostUpdateDTO;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.service.UsedPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UsedPost API", description = "UsedPost Controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UsedPostController {

    private final UsedPostService usedPostService;


    @Operation(summary = "전체 중고 게시글 조회", description = "중고 게시글 전체를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글을 성공적으로 조회 했습니다.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsedPostDTO.class)))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)
    })
    @GetMapping("/used/list")
    public ResponseEntity<List<UsedPostDTO>> getUsedList() {
       List<UsedPostDTO> postList = usedPostService.getUsedPosts();

        return ResponseEntity.ok().body(postList);
    }


    @Operation(summary = "중고 게시글 조회", description = "Post id로 post 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 조회 했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다.", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)
    })
    @GetMapping("/used/{id}")
    public ResponseEntity<UsedPostIdDTO> findPostById(@PathVariable(name = "id") Long id) {
        UsedPostIdDTO findPost = usedPostService.findPostById(id);

        return ResponseEntity.ok().body(findPost);
    }

    @Operation(summary = "중고 게시글 생성", description = "중고 게시글 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 생성 했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)
    })
    @PostMapping("/used")
    public ResponseEntity<Long> createPost(@RequestBody UsedPostCreateDTO usedPostCreateDTO ) {
        Long createdPost = usedPostService.createPost(usedPostCreateDTO);

        return ResponseEntity.ok().body(createdPost);
    }

    @Operation(summary = "중고 게시글 수정", description = "중고 게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 수정 했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
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

    @Operation(summary = "중고 게시글 삭제", description = "중고 게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 게시글을 삭제 했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다."),
    })
    @DeleteMapping("used/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "id") Long id) {
        usedPostService.deletePost(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
