package com.neo.neomarket.dto.usedPost;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsedPostDetailDTO {

    private String title;
    private String content;
    private List<String> pictures;
    private Long price;
    private String nickname;
    private LocalDateTime createTime;
    private Long views;
    private String category;

}