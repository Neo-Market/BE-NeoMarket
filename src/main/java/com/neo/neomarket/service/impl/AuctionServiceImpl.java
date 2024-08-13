package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.AuctionPostCreateDTO;
import com.neo.neomarket.dto.AuctionPostDTO;
import com.neo.neomarket.dto.BidLogDTO;
import com.neo.neomarket.entity.mysql.AuctionPostEntity;
import com.neo.neomarket.entity.mysql.PictureEntity;
import com.neo.neomarket.entity.mysql.UserEntity;
import com.neo.neomarket.exception.CustomException;
import com.neo.neomarket.exception.ErrorCode;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.service.AuctionService;
import lombok.RequiredArgsConstructor;
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

                        .title(entity.getTitle())
                        //.pictureUrl(entity.getPictures().isEmpty() ? null : entity.getPictures().get(0).getUrl()) // 첫 번째 이미지 URL 추가
                        .build())
                .toList();
    }

    @Override
    public AuctionPostDTO getAuctionPostById(Long id) {
        AuctionPostEntity auctionPostEntity = auctionPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));

        return AuctionPostDTO.builder()
                .title(auctionPostEntity.getTitle())
                .content(auctionPostEntity.getContent())
                .startPrice(auctionPostEntity.getStartPrice())
                .currentPrice(auctionPostEntity.getCurrentPrice())
                .deadline(auctionPostEntity.getDeadline())
                .category(auctionPostEntity.getCategory())
                .pictureUrls(auctionPostEntity.getPictures().stream()
                        .map(PictureEntity::getUrl)
                        .collect(Collectors.toList())) // 모든 사진 URL 리스트 추가
                .build();
    }

    //create
    @Override
    public AuctionPostCreateDTO createAuctionPost(AuctionPostCreateDTO auctionPostCreateDTO) {
        UserEntity user = userRepository.findById(auctionPostCreateDTO.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER));
        AuctionPostEntity auctionPostEntity = AuctionPostEntity.builder()
                .title(auctionPostCreateDTO.getTitle())
                .content(auctionPostCreateDTO.getContent())
                .startPrice(auctionPostCreateDTO.getStartPrice())
                .currentPrice(auctionPostCreateDTO.getStartPrice())
                .deadline(auctionPostCreateDTO.getDeadline())
                .category(auctionPostCreateDTO.getCategory())
                .user(user)
                //.status("ACTIVE") // 초기값 설정해보기
                //.pictures(new ArrayList<>()) // 초기화
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
        auctionPostRepository.save(auctionPostEntity);


        return AuctionPostCreateDTO.builder()
                .title(auctionPostEntity.getTitle())
                .content(auctionPostEntity.getContent())
                .startPrice(auctionPostEntity.getStartPrice())
                .currentPrice(auctionPostEntity.getCurrentPrice())
                .deadline(auctionPostEntity.getDeadline())
                .category(auctionPostEntity.getCategory())
                .pictureUrls(auctionPostEntity.getPictures().stream()
                        .map(PictureEntity::getUrl) // URL 리스트로 변환
                        .collect(Collectors.toList()))
                .userId(user.getId())
                .build();
    }

    @Override
    public AuctionPostDTO updateAuctionPost(Long id , AuctionPostDTO auctionPostDTO){
        AuctionPostEntity auctionPostEntity = auctionPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // DTO의 값으로 엔티티 업데이트
        auctionPostEntity.setTitle(auctionPostDTO.getTitle());
        auctionPostEntity.setContent(auctionPostDTO.getContent());
        auctionPostEntity.setCurrentPrice(auctionPostDTO.getCurrentPrice());
        auctionPostEntity.setDeadline(auctionPostDTO.getDeadline());
        auctionPostEntity.setCategory(auctionPostDTO.getCategory());

        // 이미지 URL 업데이트
//        auctionPostEntity.getPictures().clear();
//        for (String url : auctionPostDTO.getPictureUrls()) {
//            PictureEntity picture = PictureEntity.builder()
//                    .url(url)
//                    .auctionPost(auctionPostEntity)
//                    .build();
//            auctionPostEntity.getPictures().add(picture);
//        }

        AuctionPostEntity updatedEntity = auctionPostRepository.save(auctionPostEntity);

        // DTO로 변환하여 반환
        return AuctionPostDTO.builder()
                .title(updatedEntity.getTitle())
                .content(updatedEntity.getContent())
                .currentPrice(updatedEntity.getCurrentPrice())
                .deadline(updatedEntity.getDeadline())
                .category(updatedEntity.getCategory())
//                .pictureUrls(updatedEntity.getPictures().stream()
//                        .map(PictureEntity::getUrl)
//                        .collect(Collectors.toList()))

                .build();
    }

    @Override
    public void deleteAuctionPost(Long id){
        if(!auctionPostRepository.existsById(id)){
            throw new RuntimeException("게시물이 없습니다.");
        }
        auctionPostRepository.deleteById(id);
    }

}