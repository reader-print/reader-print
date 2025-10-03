package com.readerprint.backend.common.security;

import com.readerprint.backend.common.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateToken(UserDetails userDetails){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

        /*
        * Jwt 0.12.x 버전부터 개별적인 클레임 설정들 deprcated 처리 됨 -> .setSubject , setIssuedAt, ... 이런거 x
        * 하나의 맵 또는 Claims 객체로 jwt 페이로드에 해당하는 모든 정보를 구성한 다음에 설정해야 함
        * */
         Claims claims = Jwts.claims()
                 .subject(userDetails.getUsername())    // sub 클레임 = 주체
                 .issuedAt(now)                         // iat 클레임 = 발급시간
                 .expiration(expiryDate)                // exp 클레임 = 만료시간
                 .build();


        return Jwts.builder()
                .claims(claims)
                .signWith(getSigningKey())
                .compact();

    }


    /* 0.12.x 부터
    * parserBuilder -> parser()
      setSigningKey(key) -> verifyWith(key)
      parseClaimsJws(token) -> parseSignedClaims(token)
    * */
    public String getUsernameFromToken(String token){

        if(token == null || token.isEmpty()) {
            return null; // JWT 없으면 null 반환
        }

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        if(token == null || token.isEmpty()) {
            return false; // JWT 없으면 null 반환
        }

        try{
            Jwts.parser()
                    .verifyWith(getSigningKey()) // 검증 키 설정
                    .build()
                    .parseSignedClaims(token);   // 파싱 및 검증 , 예외 발생 시 catch
            return true;
        }catch (SignatureException e) {
            log.error("유효하지 않은 서명입니다.  {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT 토큰이 잘못되었습니다. : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("토큰이 만료되었습니다. : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 형식의 JWT 토큰입니다. : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("토큰이 비어있습니다. : {}", e.getMessage());
        }
        return false;
    }

    // UserDetails 기반 Authentication 객체 생성
    public Authentication getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null, // credentials는 null로
                userDetails.getAuthorities()
        );
    }

}
