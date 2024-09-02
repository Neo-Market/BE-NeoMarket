package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.user.WishDTO;
import com.neo.neomarket.entity.mysql.WishEntity;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.entity.mysql.UsedPostEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.UsedPostRepository;
import com.neo.neomarket.repository.mysql.WishRepository;
import com.neo.neomarket.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;
    private final AuctionPostRepository auctionPostRepository;
    private final UsedPostRepository usedPostRepository;

    @Override
    public Long addWish(WishDTO wishDTO) {
        UserEntity user = userRepository.findById(wishDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        WishEntity wishEntity;

        if (wishDTO.getPostType().equals("경매")) {
            AuctionPostEntity auctionPost = auctionPostRepository.findById(wishDTO.getPostId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

            wishEntity = WishEntity.builder()
                    .user(user)
                    .auctionPost(auctionPost)
                    .build();

        } else if (wishDTO.getPostType().equals("중고")) {
            UsedPostEntity usedPost = usedPostRepository.findById(wishDTO.getPostId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

            wishEntity = WishEntity.builder()
                    .user(user)
                    .usedPost(usedPost)
                    .build();

        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_POSTTYPE);
        }

        WishEntity saved = wishRepository.save(wishEntity);
        return saved.getId();
    }

    @Override
    public void removeWish(Long id) {
        WishEntity wishEntity = wishRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_WISHLIST));

        wishRepository.delete(wishEntity);
    }

}
