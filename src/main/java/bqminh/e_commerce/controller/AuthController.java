package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.ApiResponse;
import bqminh.e_commerce.dto.request.LoginRequest;
import bqminh.e_commerce.dto.request.RefreshTokenRequest;
import bqminh.e_commerce.dto.response.LoginResponse;


import bqminh.e_commerce.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Login thanh cong", authService.login(request)));
    }
    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponse<String>>logout(@RequestBody RefreshTokenRequest refreshToken){
        authService.logout(refreshToken.getRefreshToken());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Logout thanh cong",null));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<String>>refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Access token refreshed",authService.refreshAccessToken(request.getRefreshToken())));
    }

}

