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
import com.neo.neomarket.repository.mysql.PictureRepository;
import com.neo.neomarket.repository.mysql.UserRepository;
import com.neo.neomarket.service.AuctionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionPostRepository auctionPostRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

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

        if (pictures != null && !pictures.isEmpty()) {
            for (MultipartFile picture : pictures) {
                try {
                    String url = saveFile(picture);
                    PictureEntity pictureEntity = PictureEntity.builder()
                            .url(url)
                            .auctionPost(auctionPostEntity)
                            .build();
                    pictureRepository.save(pictureEntity);

                    auctionPostEntity.getPictures().add(pictureEntity);
                } catch (IOException e) {
                    throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR);
                }
            }
        }

        // 게시글 저장
        AuctionPostEntity savedEntity = auctionPostRepository.save(auctionPostEntity);

        return savedEntity.getId();
    }

    // 게시글 업데이트
    @Override
    public void updateAuctionPost(Long id, AuctionPostUpdateDTO auctionPostUpdateDTO) {
        AuctionPostEntity auctionPostEntity = auctionPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_POST));

        // DTO의 값으로 엔티티 업데이트
        auctionPostEntity.setTitle(auctionPostUpdateDTO.getTitle());
        auctionPostEntity.setContent(auctionPostUpdateDTO.getContent());
        auctionPostEntity.setDeadline(auctionPostUpdateDTO.getDeadline());
        auctionPostEntity.setCategory(auctionPostUpdateDTO.getCategory());


        auctionPostRepository.save(auctionPostEntity);

    }

    // 게시글 삭제
    @Override
    public void deleteAuctionPost(Long id) {
        if (!auctionPostRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_EXIST_POST);
        }
        auctionPostRepository.deleteById(id);
    }

    private String saveFile(MultipartFile file) throws IOException {
        // 파일의 원래 이름을 가져옵니다.
        String originFilename = file.getOriginalFilename();

        // 파일 확장자를 추출합니다.
        String extension = originFilename.substring(originFilename.lastIndexOf("."));

        // 고유한 파일 이름을 생성합니다.
        String storedFilename = UUID.randomUUID().toString() + extension;

        // 파일을 저장할 경로를 지정합니다. (업로드 디렉토리 필요)
        File dest = new File("uploads/" + storedFilename); // 예: uploads/UUID.extension

        // 파일을 해당 경로로 전송합니다.
        file.transferTo(dest);

        // 클라이언트에게 반환할 경로를 생성합니다.
        return "/uploads/" + storedFilename; // 예: /uploads/UUID.extension
    }

}

