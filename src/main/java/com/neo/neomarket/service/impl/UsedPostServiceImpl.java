package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.usedPost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedPost.UsedPostShowDTO;
import com.neo.neomarket.dto.usedPost.UsedPostDetailDTO;
import com.neo.neomarket.dto.usedPost.UsedPostUpdateDTO;
import com.neo.neomarket.entity.mysql.PictureEntity;
import com.neo.neomarket.entity.mysql.UsedPostEntity;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.mysql.PictureRepository;
import com.neo.neomarket.repository.mysql.UsedPostRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.repository.mysql.WishRepository;
import com.neo.neomarket.service.UsedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UsedPostServiceImpl implements UsedPostService {

    private final UsedPostRepository usedPostRepository;
    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsedPostShowDTO> getUsedPosts() {
        List<UsedPostShowDTO> usedPostShowDTOList = usedPostRepository.findAllByOrderByLastModifiedDateDesc().stream()
                .map(entity -> UsedPostShowDTO.builder().id(entity.getId()).title(entity.getTitle())
                        .price(entity.getPrice()).picture(entity.getPictures().get(0).getUrl())
                        .nickname(entity.getUser().getNickname()).createTime(entity.getCreatedDate())
                        .category(entity.getCategory()).build()).toList();
        return usedPostShowDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public UsedPostDetailDTO findUsedPostById(Long id) {
        UsedPostEntity findPost = usedPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        List<String> pictureUrls = new ArrayList<>();
        findPost.getPictures().forEach(pictureEntity -> pictureUrls.add(pictureEntity.getUrl()));

        return UsedPostDetailDTO.builder().title(findPost.getTitle()).content(findPost.getContent())
                .price(findPost.getPrice()).nickname(findPost.getUser().getNickname()).views(findPost.getViews())
                .createTime(findPost.getCreatedDate()).category(findPost.getCategory()).pictures(pictureUrls).build();
    }

    @Override
    @Transactional
    public Long createUsedPost(UsedPostCreateDTO usedPostCreateDTO, List<MultipartFile> pictures) {
        UserEntity user = userRepository.findById(usedPostCreateDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        UsedPostEntity usedPostEntity = UsedPostEntity.builder().title(usedPostCreateDTO.getTitle())
                .category(usedPostCreateDTO.getCategory()).content(usedPostCreateDTO.getContent())
                .price(usedPostCreateDTO.getPrice()).user(user).build();

        usedPostCreateDTO.getPictureUrls().forEach(pictureUrl -> {
            PictureEntity pictureEntity = PictureEntity.builder().url(pictureUrl).build();
            usedPostEntity.getPictures().add(pictureEntity);
        });

        UsedPostEntity created = usedPostRepository.save(usedPostEntity);
        return created.getId();
    }

    @Override
    @Transactional
    public void updateUsedPost(Long id, UsedPostUpdateDTO usedPostUpdateDTO) {
        UsedPostEntity usedPostEntity = usedPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        UserEntity user = userRepository.findByNickname(usedPostUpdateDTO.getNickname())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        UsedPostEntity entity = usedPostUpdateDTO.toEntity(user, id);
        usedPostRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteUsedPost(Long id) {
        UsedPostEntity usedPostEntity = usedPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        usedPostRepository.deleteById(id);
    }
}