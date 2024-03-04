package com.LeagueOfLegendsGG.LOL.GG.dto;

import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.Check;
import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.Checkable;
import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.ValidationContext;
import lombok.Getter;

@Getter
public class UserDto implements Checkable {
    private String uuid;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private String confirmPassword;

    @Override
    public void check(ValidationContext context) {
    }
}
