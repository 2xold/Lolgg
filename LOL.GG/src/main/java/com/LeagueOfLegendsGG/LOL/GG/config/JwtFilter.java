package com.LeagueOfLegendsGG.LOL.GG.config;

import com.LeagueOfLegendsGG.LOL.GG.service.UserService;
import com.LeagueOfLegendsGG.LOL.GG.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final String secretKey;
    //필터 문
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);
        // 토큰을 잘못보내면
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authorization 을 잘못 보냈습니다.");
            filterChain.doFilter(request, response);
            return ;
        }
        //토큰 꺼내기
        String token = authorization.split(" ")[1];
        //토큰이 expired가 됐는지 여부
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error("token is expired");
            filterChain.doFilter(request, response);
            return;
        }
        // username 토큰에서 꺼내기
        String email = JwtUtil.getUserEmail(token, secretKey);
        log.info("userEmail:{}", email);
        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("USER")));
        // 디테일 넣어주기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
