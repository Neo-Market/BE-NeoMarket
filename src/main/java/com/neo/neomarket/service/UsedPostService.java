package com.neo.neomarket.service;

import com.neo.neomarket.dto.usedpost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedpost.UsedPostDTO;
import com.neo.neomarket.dto.usedpost.UsedPostIdDTO;

import java.util.List;

public interface UsedPostService    {
    List<UsedPostDTO> getUsedPosts();
    UsedPostIdDTO findPostById(Long id);
    UsedPostCreateDTO createPost(UsedPostCreateDTO usedPostCreateDTO);
    UsedPostDTO updatePost(Long id, UsedPostDTO usedPostDTO);
    void deletePost(Long id);

//    void addWishToPost(UsedWishDTO usedWishDTO);
//    void removeWishFromPost(UsedWishDTO usedWishDTO);
}
