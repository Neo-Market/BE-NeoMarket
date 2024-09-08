package com.neo.neomarket.entity.mysql.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ADMIN", "관리자"),
    USER("USER", "사용자"),
    GUEST("GUEST", "게스트");

    private final String key;
    private final String title;
}