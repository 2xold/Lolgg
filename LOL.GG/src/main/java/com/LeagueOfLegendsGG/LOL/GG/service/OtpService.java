package com.LeagueOfLegendsGG.LOL.GG.service;

import com.LeagueOfLegendsGG.LOL.GG.config.TwilioConfig;
import com.LeagueOfLegendsGG.LOL.GG.entity.OtpEntity;
import com.LeagueOfLegendsGG.LOL.GG.entity.UserEntity;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLException;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLExceptionCode;
import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.ValidationContext;
import com.LeagueOfLegendsGG.LOL.GG.repository.OtpRepository;
import com.LeagueOfLegendsGG.LOL.GG.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OtpService {
    private final TwilioConfig twilioConfig;
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    public void sendOtp(String username, String phoneNumber) {
        userRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(user -> {
                    throw new LoLException(LoLExceptionCode.USER_IS_ALREADY_EXIST);
                });
        otpRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(user -> {
                    if (!user.getCreatedTime().isBefore(LocalDateTime.now().minusSeconds(60))) {
                        throw new LoLException(LoLExceptionCode.OTP_IS_ALREADY_SEND_TO_USER);
                    } else {
                        otpRepository.deleteById(user.getId());
                    }
                });
        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

        String otp = generateOTP();
        String otpMessage = "인증번호: " + otp;

        Message.creator(to, from, otpMessage).create();

        OtpEntity otpEntity = OtpEntity.builder()
                .username(username)
                .phoneNumber(phoneNumber)
                .otp(otp)
                .build();

        otpRepository.save(otpEntity);
    }

    public void validateOtp(String otp, String phoneNumber, ValidationContext context) {
        OtpEntity otpEntity = otpRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    throw new LoLException(LoLExceptionCode.USER_IS_NOT_EXIST);
                });
        if (!otp.equals(otpEntity.getOtp())) {
            throw new LoLException(LoLExceptionCode.IS_NOT_VALID);
        }
        if (otpEntity.getCreatedTime().isBefore(LocalDateTime.now().minusSeconds(120))) {
            throw new LoLException(LoLExceptionCode.OTP_IS_EXPIRED);
        }

        switch (context) {
            case VALIDATE_OTP -> {
                String uuid = generateSecureUUID();
                UserEntity userEntity = UserEntity.builder()
                        .uuid(uuid)
                        .phoneNumber(phoneNumber)
                        .username(otpEntity.getUsername())
                        .build();

                otpRepository.deleteById(otpEntity.getId());
                userRepository.save(userEntity);
            }
            case VALIDATE_FIND_EMAIL -> {
                otpRepository.deleteById(otpEntity.getId());
            }
        }
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
    private String generateSecureUUID() {
        return String.valueOf(UUID.randomUUID());
    }
}
