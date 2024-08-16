package com.neo.neomarket.service;

import com.neo.neomarket.dto.WishDTO;

public interface WishService {
    void addToWish(WishDTO wishDTO); // 위시리스트 추가
    void removeFromWish(WishDTO wishDTO); // 위시리스트 삭제

}