package com.neo.neomarket.dto.usedpost;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 특정 id로 게시물 조회
public class UsedPostIdDTO {
    private String title;

    private String content;

    private Long price;

    private String nickname;

    private LocalDateTime createTime;

    private Long views;

    private String category;
}