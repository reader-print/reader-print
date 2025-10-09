package com.readerprint.backend.auth.controller;

import com.readerprint.backend.auth.dto.AuthResponse;
import com.readerprint.backend.auth.dto.LoginRequest;
import com.readerprint.backend.auth.dto.RefreshRequest;
import com.readerprint.backend.auth.dto.SignupRequest;
import com.readerprint.backend.auth.service.AuthService;
import com.readerprint.backend.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name ="인증/인가")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ApiResponse<AuthResponse> signup(@Valid @RequestBody SignupRequest request){

        AuthResponse response = authService.signup(request);
        return ApiResponse.ok(response);

    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        return ApiResponse.ok(response);

    }

    @Operation(summary ="토큰 갱신")
    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request){

        AuthResponse response = authService.refresh(request.getRefreshToken());
        return ApiResponse.ok(response);
    }




}
