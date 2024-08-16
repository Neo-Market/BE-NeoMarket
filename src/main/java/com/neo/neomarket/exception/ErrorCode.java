package com.neo.neomarket.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_EXIST_POST(HttpStatus.NOT_FOUND,"게시글을 찾을 수 없습니다."),
    NOT_EXIST_USER(HttpStatus.NOT_FOUND,"유저를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,"권한이 없습니다"),
    INCORRECT_DATA(HttpStatus.UNPROCESSABLE_ENTITY, "잘못된 데이터입니다."),
    INSUFFICIENT_BALANCE(HttpStatus.PAYMENT_REQUIRED, "포인트 잔액이 부족합니다."),

    FILE_UPLOAD_ERROR(HttpStatus.NOT_FOUND, "업로드 오류가 발생했습니다. "),

    BID_AMOUNT_TOO_LOW(HttpStatus.BAD_REQUEST, "입찰 금액이 현재가보다 낮습니다."),
    NO_BID_HISTORY(HttpStatus.NOT_FOUND, "입찰 기록이 존재하지 않습니다."),
    BID_ON_OWN_POST(HttpStatus.FORBIDDEN, "자신의 게시글에는 입찰할 수 없습니다."),
    UNKNOWN(HttpStatus.NOT_FOUND,"알 수 없는 에러가 발생했습니다."),

    NOT_EXIST_WISHLIST(HttpStatus.NOT_FOUND,"위시리스트가 존재하지 않습니다."),
    NOT_EXIST_POSTTYPE(HttpStatus.NOT_FOUND,"잘못된 게시글 유형입니다.");



    private final HttpStatus httpStatus;
    private final String msg;
}
