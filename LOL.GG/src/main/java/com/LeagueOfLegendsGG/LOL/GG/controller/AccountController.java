package com.LeagueOfLegendsGG.LOL.GG.controller;

import com.LeagueOfLegendsGG.LOL.GG.dto.OtpDto;
import com.LeagueOfLegendsGG.LOL.GG.dto.UserDto;
import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.ValidationContext;
import com.LeagueOfLegendsGG.LOL.GG.service.AccountService;
import com.LeagueOfLegendsGG.LOL.GG.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final OtpService otpService;

    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(@RequestBody OtpDto dto) {
        dto.check(ValidationContext.SEND_OTP);
        otpService.sendOtp(dto.getUsername(), dto.getPhoneNumber());
        return ResponseEntity.ok().body("인증번호가 발송 되었습니다.");
    }
    @PostMapping("/email")
    public ResponseEntity<String> email(@RequestBody OtpDto dto) {
        otpService.validateOtp(dto.getOtp(),dto.getPhoneNumber(), ValidationContext.VALIDATE_FIND_EMAIL);
        return ResponseEntity.ok().body("이메일 : " + accountService.findUserEmail(dto.getPhoneNumber()));
    }

    //비번 분실 (이메일, 전번) -> (otp)
    @PostMapping("/sendEmailOtp")
    public void lostUserPassword(@RequestBody UserDto dto) throws Exception {
        dto.check(ValidationContext.SEND_EMAIL_OTP);
        accountService.sendEmailOtp(dto.getEmail(), dto.getPhoneNumber());
    }
    //(otp, 전번, 재설정 할 비번) -> (비번 재설정)
    @PostMapping("/findPassword")
    public void findUserPassword(@RequestBody OtpDto dto) {
        dto.check(ValidationContext.RESET_PASSWORD);
        accountService.resetUserPassword(dto.getOtp(), dto.getPhoneNumber(), dto.getResetPassword(),
                dto.getConfirmResetPassword());
    }
}
