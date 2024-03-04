package com.LeagueOfLegendsGG.LOL.GG.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LoLExceptionCode {
    IS_NOT_VALID(20000,"유효하지 않습니다.", HttpStatus.BAD_REQUEST),

    USERNAME_IS_EMPTY(20000,"이름이 비었습니다.", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_IS_EMPTY(20000, "전화번호가 비었습니다.", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_IS_NOT_VALID(20000, "전화번호가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_IS_EMPTY(20000, "이메일이 비었습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_IS_NOT_VALID(20000, "이메일이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_EMPTY(20000, "비밀번호가 비었습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_NOT_VALID_LENGTH(20000, "비밀번호 길이를 확인해주세요.", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_NOT_VALID(20000, "비밀번호가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),

    USER_IS_ALREADY_EXIST(20000,"사용자가 이미 있습니다.", HttpStatus.BAD_REQUEST),
    USER_IS_NOT_EXIST(20000,"사용자가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_IS_NOT_VALID(20000,"사용자가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    OTP_IS_ALREADY_SEND_TO_USER(20000, "이미 발송되었습니다.", HttpStatus.BAD_REQUEST),
    OTP_IS_EXPIRED(20000, "인증 시간을 초과했습니다.", HttpStatus.BAD_REQUEST),

    UUID_IS_NOT_EXIST(20000,"uuid가 비었습니다.",HttpStatus.BAD_REQUEST);


    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
    LoLExceptionCode(Integer code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
