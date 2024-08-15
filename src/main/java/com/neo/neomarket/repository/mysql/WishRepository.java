package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {
    // 사용자 ID와 포스트 ID로 위시리스트 항목 조회
    Optional<WishEntity> findByUserIdAndAuctionPostId(Long userId, Long auctionPostId);
    Optional<WishEntity> findByUserIdAndUsedPostId(Long userId, Long usedPostId);

    List<WishEntity> findByUserIdAndAuctionPost_Id(Long userId, Long title);
    List<WishEntity> findByUserIdAndUsedPost_Id(Long userId, Long title);
    // List<WishEntity> findAllByUserId(Long userId); 리스트 조회할 때 userid로 할지 게시글 title로 할지
}
