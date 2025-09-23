package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.ApiResponse;
import bqminh.e_commerce.dto.request.LoginRequest;
import bqminh.e_commerce.dto.response.LoginResponse;
import bqminh.e_commerce.enity.User;
import bqminh.e_commerce.repository.UserRepository;
import bqminh.e_commerce.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,JwtService jwtService,UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        User user=userRepository.findByEmail(request.getEmail());
        String accessToken=jwtService.createAccessToken(user);
        String refreshToken=jwtService.createRefreshToken(user);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Login thanh cong", new LoginResponse(accessToken,refreshToken)));
    }
}

