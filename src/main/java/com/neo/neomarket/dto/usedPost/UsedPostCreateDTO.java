package com.neo.neomarket.dto.usedPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 게시물 생성시 필요한 DTO
public class UsedPostCreateDTO {
    private String title;

    private String content;

    private Long price;

    private Long userId;

    private String category;
}