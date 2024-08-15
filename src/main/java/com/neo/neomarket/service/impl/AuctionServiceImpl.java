package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.BidLogDTO;
import com.neo.neomarket.dto.BidRequestDTO;
import com.neo.neomarket.entity.elasticsearch.AuctionLogEntity;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.elasticsearch.AuctionLogRepository;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {

    private final AuctionPostRepository auctionPostRepository;
    private final AuctionLogRepository auctionLogRepository;
    private final UserRepository userRepository;

    @Override
    public void recordBidLog(BidLogDTO bidLogDTO) {
        Logger logger = LoggerFactory.getLogger("AuctionServiceLogger");
        logger.info("{}", bidLogDTO);
    }

    @Override
    public void bidAction(BidRequestDTO bidRequestDTO) {

        AuctionPostEntity post = auctionPostRepository.findById(bidRequestDTO.getPostId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        if (bidRequestDTO.getUserId() == post.getUser().getId()) throw new CustomException(ErrorCode.BID_ON_OWN_POST);
        else if (bidRequestDTO.getBidAmount() <= post.getCurrentPrice())
            throw new CustomException(ErrorCode.BID_AMOUNT_TOO_LOW);

        UserEntity user = userRepository.findById(bidRequestDTO.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));
        if (bidRequestDTO.getBidAmount() > user.getPoint()) throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);

        BidLogDTO bidLogDTO = BidLogDTO.builder()
                .bidAmount(bidRequestDTO.getBidAmount())
                .category(post.getCategory())
                .postId(post.getId())
                .userId(user.getId())
                .build();

        recordBidLog(bidLogDTO);

        user.exchangePoint(bidRequestDTO.getBidAmount());
        post.setCurrentPrice(bidRequestDTO.getBidAmount());

        userRepository.save(user);
        auctionPostRepository.save(post);
    }

    @Override
    public void bidSuccessAction(Long postId) {
        AuctionPostEntity post = auctionPostRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        List<AuctionLogEntity> logs = auctionLogRepository.findByPostIdOrderByBidAmountDesc(postId);
        if (logs.isEmpty()) throw new CustomException(ErrorCode.NO_BID_HISTORY);

        UserEntity owner = userRepository.findById(post.getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));
        owner.chargePoint(logs.get(0).getBidAmount());
        userRepository.save(owner);

        logs.stream().skip(1)
                .forEach(log -> {
                    UserEntity user = userRepository.findById(log.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));
                    user.chargePoint(log.getBidAmount());
                    userRepository.save(user);
                });
    }

}
