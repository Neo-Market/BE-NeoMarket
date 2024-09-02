package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.home.PostShowDTO;
import com.neo.neomarket.dto.user.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.user.UserExchangeLogDTO;
import com.neo.neomarket.dto.user.UserInfoDTO;
import com.neo.neomarket.dto.user.UserSaveDTO;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.service.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long createUser(OAuth2User principal, UserSaveDTO userSaveDTO) {
        return userRepository.save(userSaveDTO.toEntity(principal)).getId();
    }

    @Override
    public UserInfoDTO getCurrentUserInfo(OAuth2User principal) {
        String email = principal.getAttribute("email");
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        return user.toUserInfoDTO();
    }

    @Override
    public UserInfoDTO getUserInfo(OAuth2User principal, Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        return user.toUserInfoDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostShowDTO> findAllWish(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        List<PostShowDTO> postShowDTOList = new ArrayList<>();

        user.getWishes().forEach(wish -> {
            PostShowDTO.PostShowDTOBuilder dtoBuilder = PostShowDTO.builder();

            if (wish.getAuctionPost() != null) {
                dtoBuilder.postId(wish.getAuctionPost().getId())
                        .postType("경매")
                        .postTitle(wish.getAuctionPost().getTitle())
                        .price(wish.getAuctionPost().getCurrentPrice())
                        .imgUrl(wish.getAuctionPost().getPictures().isEmpty() ? null
                                : wish.getAuctionPost().getPictures().get(0).getUrl())
                        .wish((long) wish.getAuctionPost().getWishes().size())
                        .createdDate(wish.getAuctionPost().getCreatedDate());
            } else if (wish.getUsedPost() != null) {
                dtoBuilder.postId(wish.getUsedPost().getId())
                        .postType("중고")
                        .postTitle(wish.getUsedPost().getTitle())
                        .price(wish.getUsedPost().getPrice())
                        .imgUrl(wish.getUsedPost().getPictures().isEmpty() ? null
                                : wish.getUsedPost().getPictures().get(0).getUrl())
                        .wish((long) wish.getUsedPost().getWishes().size())
                        .createdDate(wish.getUsedPost().getCreatedDate());
            }

            postShowDTOList.add(dtoBuilder.build());
        });

        return postShowDTOList;
    }

    @Override
    public void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO) {
        Logger logger = LoggerFactory.getLogger("UserServiceLogger");
        logger.info("{}", userExchangeLogDTO);
    }

    @Override
    @Transactional
    public void chargeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO) {
        UserEntity user = userRepository.findById(exchangeNeoPayDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        user.chargePoint(exchangeNeoPayDTO.getPoint());
        UserExchangeLogDTO exchangeLogDTO = UserExchangeLogDTO.builder()
                .userId(user.getId())
                .exchangeAmount(exchangeNeoPayDTO.getPoint())
                .payType("0")
                .payStatus("Y")
                .build();

        recordExchangeLog(exchangeLogDTO);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void exchangeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO) {
        UserEntity user = userRepository.findById(exchangeNeoPayDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        if (user.getPoint() < exchangeNeoPayDTO.getPoint()) {
            UserExchangeLogDTO exchangeFailLogDTO = UserExchangeLogDTO.builder()
                    .userId(user.getId())
                    .exchangeAmount(exchangeNeoPayDTO.getPoint())
                    .payType("1")
                    .payStatus("N : insufficient balance")
                    .build();

            recordExchangeLog(exchangeFailLogDTO);
            throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        user.exchangePoint(exchangeNeoPayDTO.getPoint());

        UserExchangeLogDTO exchangeSuccessLogDTO = UserExchangeLogDTO.builder()
                .userId(user.getId())
                .exchangeAmount(exchangeNeoPayDTO.getPoint())
                .payType("1")
                .payStatus("Y")
                .build();

        recordExchangeLog(exchangeSuccessLogDTO);

        userRepository.save(user);
    }

}