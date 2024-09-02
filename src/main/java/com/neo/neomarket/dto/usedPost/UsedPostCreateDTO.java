package com.neo.neomarket.dto.usedPost;

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
public class UsedPostCreateDTO {

    private String title;
    private String content;
    private Long price;
    private Long userId;
    private String category;
    private List<String> pictureUrls;

}