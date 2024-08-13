package com.neo.neomarket.dto;

import com.neo.neomarket.entity.mysql.UsedPostEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsedPostDTO  {
    private String title;

    private String content;

    private long price;

    private String pictures;

    private String category;


}
