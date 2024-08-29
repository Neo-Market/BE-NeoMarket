package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.auctionPost.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.auctionPost.request.AuctionPostUpdateDTO;
import com.neo.neomarket.dto.auctionPost.response.AuctionPostDTO;
import com.neo.neomarket.dto.auctionPost.response.AuctionPostReadDTO;
import com.neo.neomarket.dto.bid.BidLogDTO;
import com.neo.neomarket.dto.bid.BidRequestDTO;
import com.neo.neomarket.entity.elasticsearch.AuctionLogEntity;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.entity.mysql.PictureEntity;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.elasticsearch.AuctionLogRepository;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.PictureRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionPostRepository auctionPostRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final AuctionLogRepository auctionLogRepository;

    @Override
    public void recordBidLog(BidLogDTO bidLogDTO) {
        Logger logger = LoggerFactory.getLogger("AuctionServiceLogger");
        logger.info("{}", bidLogDTO);
    }

    @Override
    public List<AuctionPostDTO> getAuctionPosts() {
        List<AuctionPostEntity> auctionPostEntities = auctionPostRepository.findAll();
        if (auctionPostEntities.isEmpty()) {
            return List.of();
        }
        return auctionPostEntities.stream()
                .map(entity -> AuctionPostDTO.builder()
                        .id(entity.getId()) // ID 추가
                        .title(entity.getTitle())
                        .content(entity.getContent()) // 내용 추가
                        .startPrice(entity.getStartPrice()) // 시작 가격 추가
                        .currentPrice(entity.getCurrentPrice()) // 현재 가격 추가
                        .deadline(entity.getDeadline()) // 마감 기한 추가
                        .category(entity.getCategory()) // 카테고리 추가
                        //.picture(entity.getPictures().get(0).getUrl())
                        //.userId(entity.getUser().getId()) // 사용자 ID 추가
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public AuctionPostReadDTO getAuctionPostById(Long id) {
        AuctionPostEntity auctionPostEntity = auctionPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        UserEntity user = auctionPostEntity.getUser();
        String nickname = user.getNickname();
        List<String> pictureUrls = new ArrayList<>();
        auctionPostEntity.getPictures().forEach(pictureEntity -> pictureUrls.add(pictureEntity.getUrl()));
        return AuctionPostReadDTO.builder()
                .id(auctionPostEntity.getId())
                .title(auctionPostEntity.getTitle())
                .content(auctionPostEntity.getContent())
                .startPrice(auctionPostEntity.getStartPrice())
                .currentPrice(auctionPostEntity.getCurrentPrice())
                .deadline(auctionPostEntity.getDeadline())
                .category(auctionPostEntity.getCategory())
                .pictureUrls(pictureUrls)
                .nickname(nickname)
                .build();
    }

    @Override
    public void updateAuctionPost(Long id, AuctionPostUpdateDTO auctionPostUpdateDTO) {

    }

    @Override
    public Long createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO, List<MultipartFile> pictures) {
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

        auctionPostCreateDTO.getPictureUrls().forEach(pictureUrl -> {
            PictureEntity pictureEntity = PictureEntity.builder().url(pictureUrl).build();
            auctionPostEntity.getPictures().add(pictureEntity);
        });

        // 게시글 저장
        AuctionPostEntity savedEntity = auctionPostRepository.save(auctionPostEntity);
        return savedEntity.getId(); // ID 반환
    }

    @Override
    public void deleteAuctionPost(Long id) {
        if (!auctionPostRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_EXIST_POST);
        }
        auctionPostRepository.deleteById(id); // 엔티티 완전 삭제
    }

    @Override
    public void bidAction(BidRequestDTO bidRequestDTO) {

        AuctionPostEntity post = auctionPostRepository.findById(bidRequestDTO.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));
        if (bidRequestDTO.getUserId() == post.getUser().getId()) {
            throw new CustomException(ErrorCode.BID_ON_OWN_POST);
        } else if (bidRequestDTO.getBidAmount() <= post.getCurrentPrice()) {
            throw new CustomException(ErrorCode.BID_AMOUNT_TOO_LOW);
        }

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