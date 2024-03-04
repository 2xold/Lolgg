package com.LeagueOfLegendsGG.LOL.GG.exception;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private long code;
    private String message;
    private T body;
}
