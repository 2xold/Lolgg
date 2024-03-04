package com.LeagueOfLegendsGG.LOL.GG.service;

import com.LeagueOfLegendsGG.LOL.GG.entity.UserEntity;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLException;
import com.LeagueOfLegendsGG.LOL.GG.exception.LoLExceptionCode;
import com.LeagueOfLegendsGG.LOL.GG.repository.UserRepository;
import com.LeagueOfLegendsGG.LOL.GG.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder pwEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public void joinUser(String email, String password, String confirmPassword,
                          String uuid) {
        if (!password.equals(confirmPassword)) {
            throw new LoLException(LoLExceptionCode.PASSWORD_IS_NOT_VALID);
        } else {
            password = pwEncoder.encode(password);
        }

        Optional<UserEntity> existingUserEntity = userRepository.findByUuid(uuid);

        if (existingUserEntity.isPresent()) {
            UserEntity toModifyUserEntity = existingUserEntity.get();
            toModifyUserEntity.setEmail(email);
            toModifyUserEntity.setPassword(password);
            userRepository.save(toModifyUserEntity);
        } else {
            throw new LoLException(LoLExceptionCode.USER_IS_NOT_EXIST);
        }
    }
    @Value("${Jwt.secret}")
    private String secretKey;
    private Long expiredMs = 1000*60*60l;

    public String login(String email, String password) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if (!userEntity.isPresent() || !pwEncoder.matches(password, userEntity.get().getPassword())) {
            throw new LoLException(LoLExceptionCode.IS_NOT_VALID);
        }
        return JwtUtil.createJwt(email, secretKey, expiredMs);
    }
}
