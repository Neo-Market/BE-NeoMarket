package com.neo.neomarket.service;

import com.neo.neomarket.dto.UsedPostDTO;
import com.neo.neomarket.dto.UsedWishDTO;
import com.neo.neomarket.entity.mysql.UsedPostEntity;

import java.util.List;

public interface UsedPostService    {
    List<UsedPostDTO> getUsedPosts();
    UsedPostDTO findPostById(Long id);
    UsedPostDTO createPost(UsedPostDTO usedPostDTO);
    UsedPostDTO updatePost(Long id, UsedPostDTO usedPostDTO);
    void deletePost(Long id);
//    void addWishToPost(UsedWishDTO usedWishDTO);
//    void removeWishFromPost(UsedWishDTO usedWishDTO);
}
