package com.LeagueOfLegendsGG.LOL.GG.controller;

import com.LeagueOfLegendsGG.LOL.GG.dto.OtpDto;
import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.ValidationContext;
import com.LeagueOfLegendsGG.LOL.GG.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/otp")
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestBody OtpDto dto) {
        dto.check(ValidationContext.SEND_OTP);
        otpService.sendOtp(dto.getUsername(), dto.getPhoneNumber());
        return ResponseEntity.ok().body("인증번호가 발송 되었습니다.");
    }
    @PostMapping("/validate")
    public ResponseEntity<String> validateOtp(@RequestBody OtpDto dto) {
        dto.check(ValidationContext.SEND_OTP);
        otpService.validateOtp(dto.getOtp(), dto.getPhoneNumber(), ValidationContext.VALIDATE_OTP);
        return ResponseEntity.ok().body("인증 되었습니다.");
    }
}
