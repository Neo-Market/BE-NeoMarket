package com.neo.neomarket.service.impl;

import com.neo.neomarket.dto.RecentPostDTO;
import com.neo.neomarket.repository.mysql.AuctionPostRepository;
import com.neo.neomarket.repository.mysql.UsedPostRepository;
import com.neo.neomarket.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {

    private final AuctionPostRepository auctionPostRepository;
    private final UsedPostRepository usedPostRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RecentPostDTO> getRecentPosts(){
        List<RecentPostDTO> auctionPosts = auctionPostRepository.findAllByOrderByCreatedDateDesc()
                .stream()
                .map(post -> RecentPostDTO.builder()
                        .createdDate(post.getCreatedDate())
                        .postId(post.getId())
                        .postType("경매")
                        .postTitle(post.getTitle())
                        .price(post.getCurrentPrice())
                        .imgUrl(post.getPictures().isEmpty() ? null : post.getPictures().get(0).getUrl())  // 첫 번째 이미지를 사용
                        .wish((long) post.getWishes().size())
                        .build())
                .toList();

        List<RecentPostDTO> usedPosts = usedPostRepository.findAllByOrderByCreatedDateDesc()
                .stream()
                .map(post -> RecentPostDTO.builder()
                        .createdDate(post.getCreatedDate())
                        .postId(post.getId())
                        .postType("중고")
                        .postTitle(post.getTitle())
                        .price(post.getPrice())
                        .imgUrl(post.getPictures().isEmpty() ? null : post.getPictures().get(0).getUrl())  // 첫 번째 이미지를 사용
                        .wish((long) post.getWishes().size())
                        .build())
                .toList();

        return Stream.concat(auctionPosts.stream(), usedPosts.stream())
                .sorted((p1, p2) -> p2.getCreatedDate().compareTo(p1.getCreatedDate()))  // createdAt 기준으로 내림차순 정렬
                .limit(4)  // 상위 4개의 포스트만 가져옴
                .toList();
    }
}
