package com.neo.neomarket.service;

import com.neo.neomarket.dto.WishDTO;

import java.util.List;

public interface WishlistService {
    void addToWishlist(Long userId, Long PostId); // 위시리스트에 추가
    void removeFromWishlist(Long userId, Long PostId); // 위시리스트에서 삭제
}
