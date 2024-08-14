package com.neo.neomarket.dto.usedpost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsedPostUpdateDTO {
    private String title;

    private String content;

    private Long price;

    private String category;

    private String status;
}