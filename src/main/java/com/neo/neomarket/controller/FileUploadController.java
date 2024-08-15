package com.neo.neomarket.controller;

import com.neo.neomarket.service.S3FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "S3 API", description = "S3에 이미지 저장")
@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final S3FileUploadService s3FileUploadService;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Operation(summary = "이미지 업로드", description = "S3에 이미지 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지를 성공적으로 업로드 했습니다.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.", content = @Content)
    })
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> fileUrls = s3FileUploadService.uploadFiles(files, bucketName);
            return ResponseEntity.ok(fileUrls);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}