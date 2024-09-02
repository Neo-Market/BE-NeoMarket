package com.neo.neomarket.service;

import com.neo.neomarket.dto.user.WishDTO;

public interface WishService {
    Long addWish(WishDTO wishDTO);

    void removeWish(Long id);
}