package com.neo.neomarket.service;

import com.neo.neomarket.dto.usedPost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedPost.UsedPostDTO;
import com.neo.neomarket.dto.usedPost.UsedPostIdDTO;
import com.neo.neomarket.dto.usedPost.UsedPostUpdateDTO;

import java.util.List;

public interface UsedPostService    {
    List<UsedPostDTO> getUsedPosts();
    UsedPostIdDTO findPostById(Long id);
    UsedPostCreateDTO createPost(UsedPostCreateDTO usedPostCreateDTO);
    void updatePost(Long id, UsedPostUpdateDTO usedPostUpdateDTO);
    void deletePost(Long id);
}