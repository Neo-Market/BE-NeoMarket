package com.neo.neomarket.dto.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostShowDTO {

    private Long postId;
    private String postType;
    private String postTitle;
    private Long price;
    private String imgUrl;
    private Long wish;
    private LocalDateTime createdDate;

}
