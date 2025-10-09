package com.readerprint.backend.common.security;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    private static final String HEADER_AUTHORIZATION = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        log.info("[JwtAuthenticationFilter] path : {}", path);

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        //  토큰이 없는 경우: 그냥 다음 필터로 넘김 (예외 던지지 말 것)
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT 토큰 검증
        String token = getTokenFromRequest(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            var userDetails = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsernameFromToken(token));
            var authentication = jwtTokenProvider.getAuthentication(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7); // "Bearer " 제거 후 토큰만 반환. 띄어쓰기 유의할 것
        }
        return null;
    }

}
