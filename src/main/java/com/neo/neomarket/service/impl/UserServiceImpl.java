package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.user.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.user.UserExchangeLogDTO;
import com.neo.neomarket.dto.user.UserInfoDTO;
import com.neo.neomarket.dto.user.UserSaveDTO;
import com.neo.neomarket.dto.WishDTO;
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


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Long createUser(OAuth2User principal, UserSaveDTO userSaveDTO) {
        return userRepository.save(userSaveDTO.toEntity(principal)).getId();
    }

    @Override
    public UserInfoDTO getCurrentUserInfo(OAuth2User principal) {
        String email = principal.getAttribute("email");
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        return toUserInfoDTO(user);
    }

    @Override
    public UserInfoDTO getUserInfo(OAuth2User principal, Long id) {
        // You might want to add authorization check here
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        return toUserInfoDTO(user);
    }

    @Override
    public void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO) {
        Logger logger = LoggerFactory.getLogger("UserServiceLogger");
        logger.info("{}", userExchangeLogDTO);
    }

    @Override
    public List<WishDTO> findWishAll(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        List<WishDTO> wishes = new ArrayList<>();
        user.getWishes().forEach(wish -> {
            if (wish.getAuctionPost() == null && wish.getUsedPost() == null) {
                throw new CustomException(ErrorCode.INCORRECT_DATA);
            }
            Long postId = (wish.getAuctionPost() != null) ?
                    wish.getAuctionPost().getId() :
                    wish.getUsedPost().getId();
            Long postType = (wish.getAuctionPost() != null) ? 0L : 1L;
            WishDTO wishDTO = new WishDTO(user.getId(), postId, postType);
            wishes.add(wishDTO);
        });

        return wishes;
    }

    @Override
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

    private UserInfoDTO toUserInfoDTO(UserEntity user) {
        return UserInfoDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .picture(user.getPicture())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .accountNumber(user.getAccountNumber())
                .bankName(user.getBankName())
                .point(user.getPoint())
                .build();
    }

}