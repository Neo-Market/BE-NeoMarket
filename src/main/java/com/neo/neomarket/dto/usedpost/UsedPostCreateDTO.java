package com.neo.neomarket.dto.usedpost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 게시물 생성시 필요한 DTO
public class UsedPostCreateDTO {
    private String title;

    private String content;

    private long price;

    private Long userId;

    private List<String> pictures;
}
