package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.wish.PostShowDTO;
import com.neo.neomarket.dto.wish.WishDTO;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.entity.mysql.UsedPostEntity;
import com.neo.neomarket.entity.mysql.WishEntity;
import com.neo.neomarket.entity.mysql.user.UserEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.UsedPostRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
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

            wishEntity = WishEntity.builder().user(user).auctionPost(auctionPost).build();

        } else if (wishDTO.getPostType().equals("중고")) {
            UsedPostEntity usedPost = usedPostRepository.findById(wishDTO.getPostId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

            wishEntity = WishEntity.builder().user(user).usedPost(usedPost).build();

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

    @Override
    public PostShowDTO showWish(Long id) {
        WishEntity wish = wishRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_WISHLIST));

        PostShowDTO.PostShowDTOBuilder dtoBuilder = PostShowDTO.builder();

        if (wish.getAuctionPost() != null) {
            dtoBuilder.postId(wish.getAuctionPost().getId()).postType("경매").postTitle(wish.getAuctionPost().getTitle())
                    .price(wish.getAuctionPost().getCurrentPrice())
                    .imgUrl(wish.getAuctionPost().getPictures().isEmpty() ? null
                            : wish.getAuctionPost().getPictures().get(0).getUrl())
                    .wishSize((long) wish.getAuctionPost().getWishes().size())
                    .createdDate(wish.getAuctionPost().getCreatedDate());
        } else if (wish.getUsedPost() != null) {
            dtoBuilder.postId(wish.getUsedPost().getId()).postType("중고").postTitle(wish.getUsedPost().getTitle())
                    .price(wish.getUsedPost().getPrice()).imgUrl(wish.getUsedPost().getPictures().isEmpty() ? null
                            : wish.getUsedPost().getPictures().get(0).getUrl())
                    .wishSize((long) wish.getUsedPost().getWishes().size())
                    .createdDate(wish.getUsedPost().getCreatedDate());
        }
        return dtoBuilder.build();
    }
}
