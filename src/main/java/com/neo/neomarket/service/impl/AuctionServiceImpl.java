package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.auctionPost.AuctionPostCreateDTO;
import com.neo.neomarket.dto.auctionPost.AuctionPostShowDTO;
import com.neo.neomarket.dto.auctionPost.AuctionPostDetailDTO;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {

    private final AuctionPostRepository auctionPostRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final AuctionLogRepository auctionLogRepository;

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

        AuctionPostEntity created = auctionPostRepository.save(auctionPostEntity);
        return created.getId();
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

        BidLogDTO bidLogDTO = BidLogDTO.builder().bidAmount(bidRequestDTO.getBidAmount()).category(post.getCategory())
                .postId(post.getId()).userId(user.getId()).build();

        recordBidLog(bidLogDTO);

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