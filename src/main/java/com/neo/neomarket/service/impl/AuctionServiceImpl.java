package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.AuctionPostDTO;
import com.neo.neomarket.dto.BidLogDTO;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
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

    @Override
    public void recordBidLog(BidLogDTO bidLogDTO){
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
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .pictureUrl(entity.getPictures().isEmpty() ? null : entity.getPictures().get(0).getUrl()) // 첫 번째 이미지 URL 추가
                        .build())
                .toList();
    }

    public AuctionPostDTO createAuctionPost(AuctionPostDTO auctionPostDTO){
        AuctionPostEntity auctionPostEntity = AuctionPostEntity.builder()
                .id(auctionPostDTO.getId())
                .title(auctionPostDTO.getTitle())
                .content(auctionPostDTO.getContent())
                .startPrice(auctionPostDTO.getStartPrice())
                .currentPrice(auctionPostDTO.getStartPrice()) // 현재 가격과 시작 가격 동일하게 설정
                .deadline(auctionPostDTO.getDeadline())
                .category(auctionPostDTO.getCategory())
                .pictureUrls(savedEntity.getPictures().stream()
                        .map(PictureEntity::getUrl)
                        .collect(Collectors.toList())) // 사진 URL 리스트로 변환
                .build();
    }


}
