package com.LeagueOfLegendsGG.LOL.GG.controller;

import com.LeagueOfLegendsGG.LOL.GG.dto.UserDto;
import com.LeagueOfLegendsGG.LOL.GG.exception.checkable.ValidationContext;
import com.LeagueOfLegendsGG.LOL.GG.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserDto dto) {
        dto.check(ValidationContext.JOIN);
        userService.joinUser(dto.getEmail(), dto.getPassword(), dto.getConfirmPassword(),
                dto.getUuid());
        return ResponseEntity.ok().body("가입이 완료 되었습니다.");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto dto) {
        dto.check(ValidationContext.LOGIN);
        return ResponseEntity.ok().body("Bearer "+userService.login(dto.getEmail(), dto.getPassword()));
    }
    @PostMapping("info")
    public ResponseEntity<String> info(Authentication authentication) {
        return ResponseEntity.ok().body(authentication.getName());
    }
}
