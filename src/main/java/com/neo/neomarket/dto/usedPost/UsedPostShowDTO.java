package com.neo.neomarket.dto.usedPost;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsedPostShowDTO {

    private Long id;
    private String title;
    private Long price;
    private String picture;
    private String nickname;
    private LocalDateTime createTime;
    private String category;

}