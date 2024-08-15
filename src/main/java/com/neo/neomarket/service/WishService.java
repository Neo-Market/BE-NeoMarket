package com.neo.neomarket.service;

import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.entity.mysql.WishEntity;

import java.util.List;

public interface WishService {
    void addToWish(WishDTO wishDTO); // 위시리스트 추가
    void removeFromWish(WishDTO wishDTO); // 위시리스트 삭제
    List<WishDTO> findWishByTitle(Long userId, Long title);
    // List<WishEntity> getWishByUserId(Long userId);
}
