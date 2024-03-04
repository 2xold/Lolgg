package com.LeagueOfLegendsGG.LOL.GG.dto;

import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.Checkable;
import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.ValidationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpDto implements Checkable {
    private String username;
    private String phoneNumber;
    private String otp;
    private String resetPassword;
    private String ConfirmResetPassword;

    @Override
    public void check(ValidationContext context) {
    }
}
