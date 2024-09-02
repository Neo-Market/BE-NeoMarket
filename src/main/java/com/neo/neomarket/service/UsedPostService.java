package com.neo.neomarket.service;

import com.neo.neomarket.dto.usedPost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedPost.UsedPostShowDTO;
import com.neo.neomarket.dto.usedPost.UsedPostDetailDTO;
import com.neo.neomarket.dto.usedPost.UsedPostUpdateDTO;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UsedPostService {
    List<UsedPostShowDTO> getUsedPosts();

    UsedPostDetailDTO findUsedPostById(Long id);

    Long createUsedPost(UsedPostCreateDTO usedPostCreateDTO, List<MultipartFile> pictures);

    void updateUsedPost(Long id, UsedPostUpdateDTO usedPostUpdateDTO);

    void deleteUsedPost(Long id);
}