package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.WishDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishServiceImpl implements WishService {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuctionPostRepository auctionPostRepository;

    @Autowired
    private UsedPostRepository usedPostRepository;

    @Override
    public void addToWish(WishDTO wishDTO) {
        UserEntity user = userRepository.findById(wishDTO.getWishId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        WishEntity wishEntity = new WishEntity();
        wishEntity.setUser(user);

        if (wishDTO.getPostType() == 0) { // AuctionPost
            AuctionPostEntity auctionPost = auctionPostRepository.findById(wishDTO.getTitle())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
            wishEntity.setAuctionPost(auctionPost);
        } else if (wishDTO.getPostType() == 1) { // UsedPost
            UsedPostEntity usedPost = usedPostRepository.findById(wishDTO.getTitle())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
            wishEntity.setUsedPost(usedPost);
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_POSTTYPE);
        }

        wishRepository.save(wishEntity);
    }

    @Override
    public void removeFromWish(WishDTO wishDTO) {
        if (wishDTO.getPostType() == 0) { // AuctionPost
            wishRepository.findByUserIdAndAuctionPostId(wishDTO.getWishId(), wishDTO.getTitle())
                    .ifPresentOrElse(wishRepository::delete, () -> {
                        throw new CustomException(ErrorCode.NOT_EXIST_WISHLIST);
                    });
        } else if (wishDTO.getPostType() == 1) { // UsedPost
            wishRepository.findByUserIdAndUsedPostId(wishDTO.getWishId(), wishDTO.getTitle())
                    .ifPresentOrElse(wishRepository::delete, () -> {
                        throw new CustomException(ErrorCode.NOT_EXIST_WISHLIST);
                    });
        } else {
            throw new CustomException(ErrorCode.NOT_EXIST_POSTTYPE);
        }
    }

    @Override
    public List<WishDTO> findWishByTitle(Long userId, Long title) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        List<WishDTO> wishes = new ArrayList<>();

        // AuctionPost에서 title (게시글 ID)로 검색
        List<WishEntity> auctionWishes = wishRepository.findByUserIdAndAuctionPost_Id(userId, title);
        auctionWishes.forEach(wish -> {
            WishDTO wishDTO = new WishDTO(wish.getId(), wish.getAuctionPost().getId(), 0L);
            wishes.add(wishDTO);
        });

        // UsedPost에서 title (게시글 ID)로 검색
        List<WishEntity> usedWishes = wishRepository.findByUserIdAndUsedPost_Id(userId, title);
        usedWishes.forEach(wish -> {
            WishDTO wishDTO = new WishDTO(wish.getId(), wish.getUsedPost().getId(), 1L);
            wishes.add(wishDTO);
        });

        return wishes;
    }
}
