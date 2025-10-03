package com.readerprint.backend.auth.service;

import com.readerprint.backend.auth.dto.AuthResponse;
import com.readerprint.backend.auth.dto.LoginRequest;
import com.readerprint.backend.auth.dto.SignupRequest;
import com.readerprint.backend.common.security.JwtTokenProvider;
import com.readerprint.backend.user.entity.Role;
import com.readerprint.backend.user.entity.User;
import com.readerprint.backend.user.entity.UserDetail;
import com.readerprint.backend.user.entity.UserProfile;
import com.readerprint.backend.user.repository.UserDetailRepository;
import com.readerprint.backend.user.repository.UserProfileRepository;
import com.readerprint.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signup(SignupRequest request){
        log.info("회원가입 시도 : userId={}, nickname={} ", request.getUserId(), request.getNickname());


        // 중복 체크
        validateDuplicateUser(request);

        try{
            User user = User.builder()
                    .userId(request.getUserId())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .role(Role.ROLE_USER)
                    .build();

            User savedUser = userRepository.save(user);
            log.info("사용자 생성 완료 : userId= {}", savedUser.getUserId());

            //일반 사용자 인 경우 UserDetail 생성
            if (savedUser.getRole() == Role.ROLE_USER) {
                UserDetail userDetail = UserDetail.builder()
                        .user(savedUser)
                        .build();
                userDetailRepository.save(userDetail);
                log.info("UserDetail 생성 완료: userId={}", savedUser.getUserId());
            }

            // UserProfile 생성 (공통)
            UserProfile userProfile = UserProfile.builder()
                    .user(savedUser)
                    .build();
            userProfileRepository.save(userProfile);
            log.info("UserProfile 생성 완료: userId={}", savedUser.getUserId());

            // JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(savedUser);

            return AuthResponse.success(
                    savedUser.getUserId(),
                    savedUser.getEmail(),
                    savedUser.getNickname(),
                    savedUser.getRole(),
                    accessToken,
                    null, // 회원가입 시 블락정보 없음
                    "회원가입이 성공적으로 완료되었습니다."
            );

        } catch (Exception e) {
            log.error("회원가입 실패: userId={}, error={}", request.getUserId(), e.getMessage());
            throw new IllegalStateException("회원가입 처리 중 오류가 발생했습니다.");
        }


    }

    public AuthResponse login(LoginRequest request){
        log.info("로그인 시도 : userId={} ", request.getUserId());


        try {
            // 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserId(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByUserIdWithDetail(userDetails.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            // 블락 상태 체크 (일반 사용자만)
            if (user.isBlocked()) {
                log.warn("블락된 사용자 로그인 시도: userId={}, blockedUntil={}",
                        user.getUserId(), user.getUserDetail().getBlockedUntil());
                throw new IllegalStateException(
                        "계정이 " + user.getUserDetail().getBlockedUntil() + "까지 제한되었습니다.");
            }

            // JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(user);

            log.info("로그인 성공: userId={}, role={}", user.getUserId(), user.getRole());

            return AuthResponse.success(
                    user.getUserId(),
                    user.getEmail(),
                    user.getNickname(),
                    user.getRole(),
                    accessToken,
                    user.getRole() == Role.ROLE_USER && user.getUserDetail() != null ?
                            user.getUserDetail().getBlockedUntil() : null,
                    "로그인이 성공적으로 완료되었습니다."
            );

        } catch (Exception e) {
            log.error("로그인 실패: userId={}, error={}", request.getUserId(), e.getMessage());
            throw e;
        }
    }



    private void validateDuplicateUser(SignupRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }
    }




}
