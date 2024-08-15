package com.neo.neomarket.service;

import com.neo.neomarket.dto.usedpost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedpost.UsedPostDTO;
import com.neo.neomarket.dto.usedpost.UsedPostIdDTO;
import com.neo.neomarket.dto.usedpost.UsedPostUpdateDTO;

import java.util.List;

public interface UsedPostService    {
    List<UsedPostDTO> getUsedPosts();
    UsedPostIdDTO findPostById(Long id);
    Long createPost(UsedPostCreateDTO usedPostCreateDTO);
    void updatePost(Long id, UsedPostUpdateDTO usedPostUpdateDTO);
    void deletePost(Long id);
}
