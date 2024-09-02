package com.neo.neomarket.service;

import com.neo.neomarket.dto.home.RecentPostShowDTO;

import java.util.List;

public interface HomeService {
    List<RecentPostShowDTO> getRecentPosts();
}
