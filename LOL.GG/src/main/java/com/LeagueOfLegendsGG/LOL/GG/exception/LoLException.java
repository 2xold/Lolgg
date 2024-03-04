package com.LeagueOfLegendsGG.LOL.GG.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoLException extends RuntimeException {
    private long code;
    private String message;
    private HttpStatus httpStatus;

    public LoLException(LoLExceptionCode loLExceptionCode) {
        this.code = loLExceptionCode.getCode();
        this.message = loLExceptionCode.getMessage();
        this.httpStatus = loLExceptionCode.getHttpStatus();
    }
}
