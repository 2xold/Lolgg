package com.LeagueOfLegendsGG.LOL.GG.exception.checkable;

import com.LeagueOfLegendsGG.LOL.GG.dto.OtpDto;
import com.LeagueOfLegendsGG.LOL.GG.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
@Getter
@AllArgsConstructor
public enum ValidationContext {
    SEND_OTP((otpDto, userDto) -> {
        Check.checkUsername(otpDto.getUsername());
        otpDto.setPhoneNumber(Check.checkPhoneNumber(otpDto.getPhoneNumber()));
    }),
    SEND_EMAIL_OTP((otpDto, userDto) -> {
        Check.checkEmail(userDto.getEmail());
        otpDto.setPhoneNumber(Check.checkPhoneNumber(otpDto.getPhoneNumber()));
    }),
    VALIDATE_OTP((otpDto, userDto) -> {

    }),
    VALIDATE_FIND_EMAIL((otpDto, userDto) -> {

    }),
    JOIN((otpDto, userDto) -> {
        Check.checkEmail(userDto.getEmail());
        Check.checkPassword(userDto.getPassword());
        Check.checkPassword(userDto.getConfirmPassword());
    }),
    RESET_PASSWORD((otpDto, userDto) -> {
        Check.checkPassword(userDto.getPassword());
        Check.checkPassword(userDto.getConfirmPassword());
    }),
    LOGIN((otpDto, userDto) -> {
        Check.checkEmail(userDto.getEmail());
    });

    private BiConsumer<OtpDto, UserDto> checker;

}
