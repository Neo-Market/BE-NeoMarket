package com.neo.neomarket.service;

import com.neo.neomarket.dto.RecentPostDTO;

import java.util.List;

public interface HomeService {
    List<RecentPostDTO> getRecentPosts();
}
