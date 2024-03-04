package com.LeagueOfLegendsGG.LOL.GG.service;

import com.LeagueOfLegendsGG.LOL.GG.entity.OtpEntity;
import com.LeagueOfLegendsGG.LOL.GG.entity.UserEntity;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLException;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLExceptionCode;
import com.LeagueOfLegendsGG.LOL.GG.repository.OtpRepository;
import com.LeagueOfLegendsGG.LOL.GG.repository.UserRepository;
import com.LeagueOfLegendsGG.LOL.GG.util.EmailSender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final EmailSender emailSender;

    public String findUserEmail(String phoneNumber) {
        UserEntity userEntity = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new LoLException(LoLExceptionCode.PHONE_NUMBER_IS_NOT_VALID));
        return userEntity.getEmail();
    }
    public void sendEmailOtp(String email, String phoneNumber) throws Exception {
        UserEntity userEntity = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new LoLException(LoLExceptionCode.PHONE_NUMBER_IS_NOT_VALID));

        if (!userEntity.getEmail().equals(email)) {
            throw new LoLException(LoLExceptionCode.USER_IS_NOT_VALID);
        }

        otpRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(user -> {
                    if (!user.getCreatedTime().isBefore(LocalDateTime.now().minusSeconds(60))) {
                        throw new LoLException(LoLExceptionCode.OTP_IS_ALREADY_SEND_TO_USER);
                    } else {
                        otpRepository.deleteById(user.getId());
                    }
                });

        String otp = emailSender.emailSender(email);

        OtpEntity otpEntity = OtpEntity.builder()
                .phoneNumber(phoneNumber)
                .otp(otp)
                .build();

        otpRepository.save(otpEntity);
    }
    private final PasswordEncoder pwEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Transactional
    public void resetUserPassword(String otp, String phoneNumber, String resetPassword,
                             String confirmResetPassword) {
        OtpEntity otpEntity = otpRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new LoLException(LoLExceptionCode.PHONE_NUMBER_IS_NOT_VALID));

        if (!otpEntity.getOtp().equals(otp)) {
            throw new LoLException(LoLExceptionCode.USER_IS_NOT_EXIST);
        }
        if (otpEntity.getCreatedTime().isBefore(LocalDateTime.now().minusSeconds(120))) {
            throw new LoLException(LoLExceptionCode.OTP_IS_EXPIRED);
        }

        if (!resetPassword.equals(confirmResetPassword)) {
            throw new LoLException(LoLExceptionCode.PASSWORD_IS_NOT_VALID);
        } else {
            resetPassword = pwEncoder.encode(resetPassword);
        }

        Optional<UserEntity> existingUserEntity = userRepository.findByPhoneNumber(phoneNumber);

        if (existingUserEntity.isPresent()) {
            UserEntity toModifyUserEntity = existingUserEntity.get();
            toModifyUserEntity.setPassword(resetPassword);
            userRepository.save(toModifyUserEntity);
        } else {
            throw new LoLException(LoLExceptionCode.USER_IS_NOT_EXIST);
        }
        otpRepository.deleteByPhoneNumber(phoneNumber);
    }
}
