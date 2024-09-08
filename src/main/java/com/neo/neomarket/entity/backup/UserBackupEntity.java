package com.neo.neomarket.entity.backup;

import com.neo.neomarket.entity.mysql.user.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_BACKUP")
public class UserBackupEntity {

    @Id
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(unique = true)
    private String email;


    @Column(length = 20, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String address;

    private String accountNumber;

    @Column(nullable = false)
    private String bankName;

    private LocalDateTime inactiveDate;

    @Column(nullable = false)
    private Long point;

    @Column(name = "deleted_at", nullable = false, updatable = false)
    private LocalDateTime deletedAt;
}
