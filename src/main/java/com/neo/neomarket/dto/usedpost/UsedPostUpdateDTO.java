package com.neo.neomarket.dto.usedpost;

import com.neo.neomarket.entity.mysql.PictureEntity;
import com.neo.neomarket.entity.mysql.UsedPostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsedPostUpdateDTO {
    private String title;

    private String content;

    private Long price;

    private String category;

    private List<String> pictures;

    public UsedPostEntity toEntity(UsedPostEntity usedPostEntity) {
        UsedPostEntity newEntity = UsedPostEntity.builder()
                .id(usedPostEntity.getId())
                .views(usedPostEntity.getViews())
                .user(usedPostEntity.getUser())
                .title(this.title)
                .content(this.content)
                .price(this.price)
                .category(this.category)
                .build();

        newEntity.setCreatedDate(usedPostEntity.getCreatedDate());

        List<PictureEntity> pictureEntities = this.pictures.stream()
                .map(picture -> PictureEntity.builder()
                        .url(picture)
                        .usedPost(newEntity)  // 관계 설정
                        .build())
                .collect(Collectors.toList());

        newEntity.setPictures(pictureEntities);

        return newEntity;
    }
}
