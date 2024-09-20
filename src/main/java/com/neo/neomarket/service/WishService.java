package com.neo.neomarket.service;

import com.neo.neomarket.dto.wish.PostShowDTO;
import com.neo.neomarket.dto.wish.WishDTO;

public interface WishService {
    Long addWish(WishDTO wishDTO);

    void removeWish(Long id);

    PostShowDTO showWish(Long id);
}