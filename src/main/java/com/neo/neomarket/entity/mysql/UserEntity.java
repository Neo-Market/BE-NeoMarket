package com.neo.neomarket.entity.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(length = 20)
    private String username;

    @Column(unique = true)
    private String email;

    private String profile;

    @Column(nullable = false)
    private String role;

    private String address;

    private String accountNumber;

    @Column(nullable = false)
    private String bankName;

    private LocalDateTime inactiveDate;

    @Builder.Default
    @Column(nullable = false)
    private Long point = 0L;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private final List<AuctionPostEntity> auctionPostEntities =new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private final List<WishEntity> wishes =new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private final List<UsedPostEntity> usedPosts  =new ArrayList<>();

    public void chargePoint(Long neoPoint){
        this.point += neoPoint;
    }

    public void exchangePoint(Long neoPoint){
        this.point -= neoPoint;
    }
}
