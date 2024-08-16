package com.neo.neomarket.dto;

import com.neo.neomarket.controller.WishController;
import com.neo.neomarket.entity.mysql.WishEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishDTO {

    @Schema(description = "유저 id")
    private Long userId;

    @Schema(description = "게시글 id")
    private Long postId;

    @Schema(description = "게시글 타입")
    private Long postType;

}