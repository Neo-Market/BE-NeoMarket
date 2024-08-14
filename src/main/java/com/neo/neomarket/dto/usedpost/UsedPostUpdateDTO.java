package com.neo.neomarket.dto.usedpost;

import com.neo.neomarket.entity.mysql.UsedPostEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsedPostUpdateDTO {

    private Long id;
    private String title;
    private String content;
    private Long price;
    private Long views ;
    private String category;

    public UsedPostEntity toEntity(Long id, String title, String content, Long price, String category) {
        return UsedPostEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .price(price)
                .category(category)
                .views(this.views)
                .build();
    }

}
