package com.neo.neomarket.service.impl;
import com.neo.neomarket.dto.usedpost.UsedPostCreateDTO;
import com.neo.neomarket.dto.usedpost.UsedPostDTO;
import com.neo.neomarket.dto.usedpost.UsedPostIdDTO;
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
                        .userId(entity.getId())
                        .pictures("entity.getPictures().get(0).getUrl()")
                        .createTime(entity.getCreatedDate())
                        .build())
                .toList();
    }

    // ID로 게시글 조회
    @Override
    public UsedPostIdDTO findPostById(Long id) {
        // 게시글을 ID로 조회합니다.
        UsedPostEntity findPost = usedPostRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        List<String> pictureUrls = new ArrayList<>();
        findPost.getPictures().forEach(pictureEntity -> pictureUrls.add(pictureEntity.getUrl()));

        UsedPostIdDTO usedPostIdDTO = UsedPostIdDTO.builder()
                .title(findPost.getTitle())
                .content(findPost.getContent())
                .price(findPost.getPrice())
                .userId(findPost.getUser().getId())
                .pictureUrls(pictureUrls)
                .createTime(findPost.getCreatedDate())
                .views(findPost.getViews())
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
                .content(usedPostCreateDTO.getContent())
                .price(usedPostCreateDTO.getPrice())
                .user(user)
                .build();

        // Entity를 데이터베이스에 저장
        UsedPostEntity create = usedPostRepository.save(usedPostEntity);

        // 저장된 Entity를 다시 DTO로 변환하여 반환
        return UsedPostCreateDTO.builder()
                .title(create.getTitle())
                .content(create.getContent())
                .price(create.getPrice())
                .build();
    }

    // 게시글 수정
    @Override
    public UsedPostDTO updatePost(Long id, UsedPostDTO usedPostDTO) {
        // 게시글을 ID로 조회합니다.
        UsedPostEntity upost = usedPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found"));

        // 게시글이 존재하는 경우, 업데이트할 필드를 설정합니다.
        upost.setTitle(usedPostDTO.getTitle());
//        upost.setContent(usedPostDTO.getContent());
//        post.setStatus(usedPostDTO.getStatus());
        upost.setPrice(usedPostDTO.getPrice());

        // 업데이트된 게시글을 저장합니다.
        UsedPostEntity updatedPost = usedPostRepository.save(upost);

        // Entity를 DTO로 변환하여 반환합니다.
        return UsedPostDTO.builder()
                .title(updatedPost.getTitle())
//                .content(updatedPost.getContent())
                .price(updatedPost.getPrice())
                .build();
    }

    // 게시글 삭제
    @Override
    public void deletePost(Long id) {
        usedPostRepository.deleteById(id);
    }

//    // 게시글을 wish에 저장하는 메소드
//    @Override
//    public void addWishToPost(UsedWishDTO usedWishDTO) {
//        UsedPostEntity post = usedPostRepository.findById(usedWishDTO.getPostId())
//                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + usedWishDTO.getPostId()));
//        UserEntity user = userRepository.findById(usedWishDTO.getUserId())
//                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + usedWishDTO.getUserId()));
//
//        // 중복 체크
//        if (wishRepository.findByUsedPostIdAndUserId(UsedWishDTO usedWishDTO).isPresent()) {
//            throw new IllegalStateException("Wish already exists for postId " + usedWishDTO.getPostId() + " and userId " + usedWishDTO.getUserId());
//
//        }
//
//        // WishEntity 생성 및 저장
//        WishEntity wish = WishEntity.builder()
//                .usedPost(post)
//                .user(user)
//                .build();
//        wishRepository.save(wish);
//    }
//
//    // 게시글의 wish를 삭제하는 메소드
//    @Override
//    public void removeWishFromPost(UsedWishDTO usedWishDTO) {
//        if (wishRepository.findByUsedPostIdAndUserId(usedWishDTO.getPostId(), usedWishDTO.getUserId()).isEmpty()) {
//            throw new EntityNotFoundException("Wish not found for postId " + usedWishDTO.getPostId() + " and userId " + usedWishDTO.getUserId());
//        }
//        wishRepository.deleteByUsedPostIdAndUserId(UsedWishDTO usedWishDTO);
//    }

}
