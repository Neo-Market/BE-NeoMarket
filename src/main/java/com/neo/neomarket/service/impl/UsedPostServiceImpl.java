package com.neo.neomarket.service.impl;
import com.neo.neomarket.dto.usedpost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedpost.UsedPostDTO;
import com.neo.neomarket.dto.usedpost.UsedPostIdDTO;
import com.neo.neomarket.dto.usedpost.UsedPostUpdateDTO;
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

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RequiredArgsConstructor
@Service
public class UsedPostServiceImpl implements UsedPostService {

    private final UsedPostRepository usedPostRepository;
    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    @Override
    public List<UsedPostDTO> getUsedPosts() {
        // 전체 게시글을 조회
        List<UsedPostEntity> usedPostEntities = usedPostRepository.findAll();

        // 게시글이 없으면 예외 발생
        if (usedPostEntities.isEmpty()) {
            return List.of();
        }

        // Entity 리스트를 DTO 리스트로 변환
        return usedPostEntities.stream()
                .map(entity -> UsedPostDTO.builder()
                        .title(entity.getTitle())
                        .price(entity.getPrice())
                        .createTime(entity.getCreatedDate())
                        .category(entity.getCategory())
                        .nickname(entity.getUser().getNickname())
                        .build())
                .toList();
    }

    // ID로 게시글 조회
    @Override
    public UsedPostIdDTO findPostById(Long id) {
        // 게시글을 ID로 조회합니다.
        UsedPostEntity findPost = usedPostRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        String nickname = findPost.getUser().getNickname();
        List<String> pictureUrls = new ArrayList<>();
        findPost.getPictures().forEach(pictureEntity -> pictureUrls.add(pictureEntity.getUrl()));

        UsedPostIdDTO usedPostIdDTO = UsedPostIdDTO.builder()
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .price(findPost.getPrice())
                .nickname(nickname)
                .createTime(findPost.getCreatedDate())
                .views(findPost.getViews())
                .category(findPost.getCategory())
                .build();

        return usedPostIdDTO;
    }

    // 게시글 생성
    @Override
    public UsedPostCreateDTO createPost(UsedPostCreateDTO usedPostCreateDTO) {
        UserEntity user = userRepository.findById(usedPostCreateDTO.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        // DTO를 Entity로 변환
        UsedPostEntity usedPostEntity = UsedPostEntity.builder()
                .title(usedPostCreateDTO.getTitle())
                .category(usedPostCreateDTO.getCategory())
                .content(usedPostCreateDTO.getContent())
                .price(usedPostCreateDTO.getPrice())
                .status(usedPostCreateDTO.getStatus())
                .user(user)
                .build();

        // Entity를 데이터베이스에 저장
        UsedPostEntity create = usedPostRepository.save(usedPostEntity);

        // 저장된 Entity를 다시 DTO로 변환하여 반환
        UsedPostCreateDTO CreateDTO = UsedPostCreateDTO.builder()
                .title(create.getTitle())
                .category(create.getCategory())
                .content(create.getContent())
                .price(create.getPrice())
                .userId(create.getUser().getId())
                .status(create.getStatus())
                .build();

        return CreateDTO;
    }

    // 게시글 수정
    @Override
    public UsedPostUpdateDTO updatePost(Long id, UsedPostUpdateDTO usedPostUpdateDTO) {
        // 게시글을 ID로 조회합니다.
        UsedPostEntity upost = usedPostRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        // 게시글이 존재하는 경우, 업데이트할 필드를 설정합니다.
        upost.setTitle(usedPostUpdateDTO.getTitle());
        upost.setCategory(usedPostUpdateDTO.getCategory());
        upost.setContent(usedPostUpdateDTO.getContent());
        upost.setPrice(usedPostUpdateDTO.getPrice());
        upost.setStatus(usedPostUpdateDTO.getStatus());

        // 업데이트된 게시글을 저장합니다.
        UsedPostEntity updatedPost = usedPostRepository.save(upost);

        // Entity를 DTO로 변환하여 반환합니다.
        UsedPostUpdateDTO UpdatDTO = UsedPostUpdateDTO.builder()
                .title(updatedPost.getTitle())
                .category(updatedPost.getCategory())
                .content(updatedPost.getContent())
                .price(updatedPost.getPrice())
                .status(updatedPost.getStatus())
                .build();

        return UpdatDTO;
    }

    // 게시글 삭제
    @Override
    public void deletePost(Long id) {
        if(!usedPostRepository.existsById(id)){
            throw new RuntimeException("게시물이 없습니다.");
        }
        usedPostRepository.deleteById(id);
    }
}
