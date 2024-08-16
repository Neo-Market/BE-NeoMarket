package com.neo.neomarket.dto.usedpost;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 전체 게시물 조회 DTO
public class UsedPostDTO  {
    private Long id;

    private String title;

    private Long price;

    private String pictures;

    private String nickname;

    private LocalDateTime createTime;

    private String category;

    private String picture;

}
