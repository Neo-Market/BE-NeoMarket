package com.neo.neomarket.dto.usedPost;

import com.neo.neomarket.entity.mysql.UsedPostEntity;
import com.neo.neomarket.entity.mysql.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsedPostUpdateDTO {

    private String title;
    private String content;
    private Long price;
    private String category;
    private Long views;
    private String nickname;

    public UsedPostEntity toEntity(UserEntity user, Long id) {
        return UsedPostEntity.builder()
                .id(id)
                .title(this.title)
                .content(this.content)
                .price(this.price)
                .category(this.category)
                .views(this.views)
                .user(user)
                .build();
    }
}