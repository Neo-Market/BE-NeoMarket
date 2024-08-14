package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.*;
import com.neo.neomarket.dto.Auction.request.AuctionPostCreateDTO;
import com.neo.neomarket.dto.Auction.request.AuctionPostUpdateDTO;
import com.neo.neomarket.dto.response.AuctionPostDTO;
import com.neo.neomarket.dto.response.AuctionPostReadDTO;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.entity.mysql.PictureEntity;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.service.AuctionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionPostRepository auctionPostRepository;
    private final UserRepository userRepository;

    @Override
    public void recordBidLog(BidLogDTO bidLogDTO) {
        Logger logger = LoggerFactory.getLogger("AuctionServiceLogger");
        logger.info("{}", bidLogDTO);
    }

    // 게시글 리스트 조회
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
                        .pictureUrls(entity.getPictures().stream()
                                .map(PictureEntity::getUrl) // 사진 URL 추가
                                .collect(Collectors.toList())) // URL 리스트 추가
                        .userId(entity.getUser().getId()) // 사용자 ID 추가
                        .status(entity.getStatus()) // 상태 추가
                        .build())
                .collect(Collectors.toList());
    }

    // 게시글 상세보기
    @Override
    public AuctionPostReadDTO getAuctionPostById(Long id) {
        AuctionPostEntity auctionPostEntity = auctionPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        UserEntity user = auctionPostEntity.getUser();
        if (user == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }

        String nickname = user.getNickname();

        return AuctionPostReadDTO.builder()
                .id(auctionPostEntity.getId())
                .title(auctionPostEntity.getTitle())
                .content(auctionPostEntity.getContent())
                .startPrice(auctionPostEntity.getStartPrice())
                .currentPrice(auctionPostEntity.getCurrentPrice())
                .deadline(auctionPostEntity.getDeadline())
                .category(auctionPostEntity.getCategory())
                .pictureUrls(auctionPostEntity.getPictures().stream()
                        .map(PictureEntity::getUrl)
                        .collect(Collectors.toList()))
                .nickname(nickname)
                .build();
    }

    // 게시글 생성
    @Override
    public AuctionPostCreateDTO createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO) {
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

        // PictureEntity 리스트 생성
        if (auctionPostCreateDTO.getPictureUrls() != null) {
            for (String url : auctionPostCreateDTO.getPictureUrls()) {
                PictureEntity picture = PictureEntity.builder()
                        .url(url)
                        .auctionPost(auctionPostEntity) // 연결 설정
                        .build();
                auctionPostEntity.getPictures().add(picture); // 리스트에 추가
            }
        }

        // 게시글 저장
        AuctionPostEntity savedEntity = auctionPostRepository.save(auctionPostEntity);

        // DTO로 변환하여 반환
        return AuctionPostCreateDTO.builder()
                .userId(savedEntity.getUser().getId())
                .build();
    }

    // 게시글 업데이트
    @Override
    public AuctionPostUpdateDTO updateAuctionPost(Long id, AuctionPostUpdateDTO auctionPostUpdateDTO) {
        AuctionPostEntity auctionPostEntity = auctionPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        // DTO의 값으로 엔티티 업데이트
        auctionPostEntity.setTitle(auctionPostUpdateDTO.getTitle());
        auctionPostEntity.setContent(auctionPostUpdateDTO.getContent());
        auctionPostEntity.setDeadline(auctionPostUpdateDTO.getDeadline());
        auctionPostEntity.setCategory(auctionPostUpdateDTO.getCategory());

        AuctionPostEntity updatedEntity = auctionPostRepository.save(auctionPostEntity);

        // DTO로 변환하여 반환
        return AuctionPostUpdateDTO.builder()
                .title(updatedEntity.getTitle())
                .content(updatedEntity.getContent())
                .deadline(updatedEntity.getDeadline())
                .category(updatedEntity.getCategory())
                .build();
    }

    // 게시글 삭제
    @Override
    public void deleteAuctionPost(Long id) {
        if (!auctionPostRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_EXIST_POST);
        }
        auctionPostRepository.deleteById(id); // 엔티티 완전 삭제
    }
}
