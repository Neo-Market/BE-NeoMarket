package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.entity.mysql.WishEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.repository.mysql.WishRepository;
import com.neo.neomarket.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishRepository wishRepository;
    private final AuctionPostRepository auctionPostRepository;
    private final UserRepository userRepository;

    @Override
    public void addToWishlist(Long userId, Long auctionPostId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        PostEntity Post = auctionPostRepository.findById(auctionPostId)

        WishEntity wish = WishEntity.builder()
                .user(user)
                .auctionPost(auctionPost)
                .build();

        wishRepository.save(wish);
    }

    @Override
    public void removeFromWishlist(Long userId, Long auctionPostId) {
        WishEntity wish = wishRepository.findAll().stream()
                .filter(w -> w.getUser().getId().equals(userId) && w.getAuctionPost().getId().equals(auctionPostId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        wishRepository.delete(wish);
    }


}
