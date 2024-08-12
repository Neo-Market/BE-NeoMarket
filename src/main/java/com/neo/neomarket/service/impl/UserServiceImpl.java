package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.ExchangeNeoPayDTO;
import com.neo.neomarket.dto.UserDTO;
import com.neo.neomarket.dto.UserExchangeLogDTO;
import com.neo.neomarket.dto.WishDTO;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void recordExchangeLog(UserExchangeLogDTO userExchangeLogDTO) {
        Logger logger = LoggerFactory.getLogger("UserServiceLogger");
        logger.info("{}", userExchangeLogDTO);
    }

    @Override
    public List<WishDTO> findWishAll(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
        List<WishDTO> wishes = new ArrayList<>();
        user.getWishes().forEach(wish -> {
            if (wish.getAuctionPost() == null && wish.getUsedPost() == null)
                throw new IllegalStateException("Wishlist contains invalid data: No associated post found.");
            String title = wish.getAuctionPost() == null ? wish.getUsedPost().getTitle() : wish.getAuctionPost().getTitle();
            WishDTO wishDTO = new WishDTO(wish.getId(), title);
            wishes.add(wishDTO);
        });

        return wishes;
    }

    @Override
    public UserDTO userInfo(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));

        UserDTO userDTO = UserDTO.builder()
                .point(user.getPoint())
                .accountNumber(user.getAccountNumber())
                .address(user.getAddress())
                .email(user.getEmail())
                .bankName(user.getBankName())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .build();

        return userDTO;
    }

    @Override
    public void chargeNeoPay(ExchangeNeoPayDTO exchangeNeoPayDTO) {
        UserEntity user = userRepository.findById(exchangeNeoPayDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("user not found"));
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
        UserEntity user = userRepository.findById(exchangeNeoPayDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("user not found"));
        if(user.getPoint() < exchangeNeoPayDTO.getPoint()) {
            UserExchangeLogDTO exchangeFailLogDTO = UserExchangeLogDTO.builder()
                    .userId(user.getId())
                    .exchangeAmount(exchangeNeoPayDTO.getPoint())
                    .payType("1")
                    .payStatus("N : insufficient balance")
                    .build();

            recordExchangeLog(exchangeFailLogDTO);
            //TODO: 잔액 부족 커스텀 예외
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
