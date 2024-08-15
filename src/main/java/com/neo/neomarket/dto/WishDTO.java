package com.neo.neomarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishDTO {


    @Schema(description = "위시 id")
    private Long wishId;

    @Schema(description = "게시글 id")
    private Long title;

    @Schema(description = "게시글 타입")
    private Long postType;


}
