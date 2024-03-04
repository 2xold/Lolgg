package com.LeagueOfLegendsGG.LOL.GG.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LOLExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CommonResponse<?>> handle(LoLException lolGgException) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        commonResponse.setCode(lolGgException.getCode());
        commonResponse.setMessage(lolGgException.getMessage());
        commonResponse.setBody(null);
        return ResponseEntity.status(lolGgException.getHttpStatus()).body(commonResponse);
    }
}
