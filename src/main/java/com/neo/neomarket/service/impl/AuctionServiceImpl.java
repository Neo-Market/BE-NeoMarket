package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.auctionPost.AuctionPostCreateDTO;
import com.neo.neomarket.dto.auctionPost.AuctionPostShowDTO;
import com.neo.neomarket.dto.auctionPost.AuctionPostDetailDTO;
import com.neo.neomarket.dto.bid.BidLogDTO;
import com.neo.neomarket.dto.bid.BidRequestDTO;
import com.neo.neomarket.entity.elasticsearch.AuctionLogEntity;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.entity.mysql.PictureEntity;
import com.neo.neomarket.entity.mysql.user.UserEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.elasticsearch.AuctionLogRepository;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.PictureRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.service.AuctionService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {

    private final AuctionPostRepository auctionPostRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final AuctionLogRepository auctionLogRepository;
    private final S3FileUploadService s3FileUploadService;

    @Override
    @Transactional(readOnly = true)
    public List<AuctionPostShowDTO> getAuctionPosts() {
        List<AuctionPostShowDTO> auctionPostShowDTOList = auctionPostRepository.findAllByOrderByLastModifiedDateDesc()
                .stream()
                .map(entity -> AuctionPostShowDTO.builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .picture(entity.getPictures().get(0).getUrl())
                        .content(entity.getContent())
                        .startPrice(entity.getStartPrice())
                        .currentPrice(entity.getCurrentPrice())
                        .deadline(entity.getDeadline())
                        .category(entity.getCategory())
                        .nickname(entity.getUser().getNickname())
                        .build())
                .toList();
        return auctionPostShowDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionPostDetailDTO getAuctionPostById(Long id) {
        AuctionPostEntity findPost = auctionPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        List<String> pictureUrls = new ArrayList<>();
        findPost.getPictures().forEach(pictureEntity -> pictureUrls.add(pictureEntity.getUrl()));

        return AuctionPostDetailDTO.builder().title(findPost.getTitle()).content(findPost.getContent())
                .startPrice(findPost.getStartPrice()).currentPrice(findPost.getCurrentPrice())
                .deadline(findPost.getDeadline()).category(findPost.getCategory())
                .nickname(findPost.getUser().getNickname()).pictures(pictureUrls).build();
    }

    @Override
    @Transactional
    public Long createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO, MultipartFile file) {
        UserEntity user = userRepository.findById(auctionPostCreateDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

        AuctionPostEntity auctionPostEntity = AuctionPostEntity.builder()
                .title(auctionPostCreateDTO.getTitle())
                .content(auctionPostCreateDTO.getContent())
                .startPrice(auctionPostCreateDTO.getStartPrice())
                .currentPrice(auctionPostCreateDTO.getStartPrice())
                .deadline(auctionPostCreateDTO.getDeadline())
                .category(auctionPostCreateDTO.getCategory())
                .user(user)
                .build();

        AuctionPostEntity createdPost = auctionPostRepository.save(auctionPostEntity);

        String pictureUrl;
        try {
            pictureUrl = s3FileUploadService.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PictureEntity pictureEntity = PictureEntity.builder()
                .url(pictureUrl)
                .auctionPost(auctionPostEntity)  // 관계 설정
                .build();

        pictureRepository.save(pictureEntity);

        return createdPost.getId();
    }

    @Override
    @Transactional
    public void deleteAuctionPost(Long id) {
        AuctionPostEntity auctionPostEntity = auctionPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        auctionPostRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void bidAction(BidRequestDTO bidRequestDTO) {

        AuctionPostEntity post = auctionPostRepository.findById(bidRequestDTO.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        if (bidRequestDTO.getUserId() == post.getUser().getId()) {
            throw new CustomException(ErrorCode.BID_ON_OWN_POST);
        } else if (bidRequestDTO.getBidAmount() <= post.getCurrentPrice()) {
            throw new CustomException(ErrorCode.BID_AMOUNT_TOO_LOW);
        }

        UserEntity user = userRepository.findById(bidRequestDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));
        if (bidRequestDTO.getBidAmount() > user.getPoint()) {
            throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        BidLogDTO bidLogDTO = BidLogDTO.builder().bidAmount(bidRequestDTO.getBidAmount())
                .category(post.getCategory())
                .postId(post.getId()).userId(user.getId()).build();

        recordBidLog(bidLogDTO);

        // 특정 postId에 대한 가장 최신 입찰 로그 조회
        Optional<AuctionLogEntity> currentTopBidOptional = auctionLogRepository.findTopByPostIdOrderByBidAmountDesc(post.getId());

        if (currentTopBidOptional.isPresent()) {
            AuctionLogEntity currentTopBid = currentTopBidOptional.get();

            // 현재 최고 입찰자에게 포인트를 반환하는 로직
            if (!currentTopBid.getUserId().equals(user.getId())) {
                UserEntity currentTopBidder = userRepository.findById(currentTopBid.getUserId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));

                // 포인트 추가
                currentTopBidder.chargePoint(currentTopBid.getBidAmount());
                userRepository.save(currentTopBidder);  // 변경된 사용자 저장
            }
        }


        user.exchangePoint(bidRequestDTO.getBidAmount());
        post.setCurrentPrice(bidRequestDTO.getBidAmount());

        userRepository.save(user);
        auctionPostRepository.save(post);
    }

    @Override
    @Transactional
    public void bidSuccessAction(Long postId) {
        AuctionPostEntity post = auctionPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        List<AuctionLogEntity> logs = auctionLogRepository.findByPostIdOrderByBidAmountDesc(postId);
        if (logs.isEmpty()) {
            throw new CustomException(ErrorCode.NO_BID_HISTORY);
        }

        UserEntity owner = userRepository.findById(post.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));
        owner.chargePoint(logs.get(0).getBidAmount());
        userRepository.save(owner);

        logs.stream().skip(1).forEach(log -> {
            UserEntity user = userRepository.findById(log.getUserId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));
            user.chargePoint(log.getBidAmount());
            userRepository.save(user);
        });
    }

    @Override
    public void recordBidLog(BidLogDTO bidLogDTO) {
        Logger logger = LoggerFactory.getLogger("AuctionServiceLogger");
        logger.info("{}", bidLogDTO);
    }

}