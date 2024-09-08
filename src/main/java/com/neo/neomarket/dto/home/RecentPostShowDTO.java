package com.neo.neomarket.dto.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecentPostShowDTO {

    private Long postId;
    private String postType;
    private String postTitle;
    private Long price;
    private String imgUrl;
    private Long wishSize;
    private String nickname;
    private LocalDateTime createdDate;

}