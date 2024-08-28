package com.neo.neomarket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecentPostDTO {
    private Long postId;
    private String postType;
    private String postTitle;
    private Long price;
    private String imgUrl;
    private Long wish;
    private LocalDateTime createdDate;
}

