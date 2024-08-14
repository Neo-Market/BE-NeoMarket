package com.neo.neomarket.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_EXIST_POST(HttpStatus.NOT_FOUND,"게시글을 찾을 수 없습니다."),
    NOT_EXIST_USER(HttpStatus.NOT_FOUND,"유저를 찾을 수 없습니다."),
    INCORRECT_DATA(HttpStatus.UNPROCESSABLE_ENTITY, "잘못된 데이터입니다."),
    INSUFFICIENT_BALANCE(HttpStatus.PAYMENT_REQUIRED, "포인트 잔액이 부족합니다."),
    UNKNOWN(HttpStatus.NOT_FOUND,"알 수 없는 에러가 발생했습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.NOT_FOUND, "업로드 오류가 발생했습니다. ");

    private final HttpStatus httpStatus;
    private final String msg;
}
