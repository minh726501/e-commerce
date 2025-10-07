package bqminh.e_commerce.service;

import bqminh.e_commerce.dto.request.LoginRequest;
import bqminh.e_commerce.dto.request.RegisterRequest;
import bqminh.e_commerce.dto.response.LoginResponse;
import bqminh.e_commerce.enity.RefreshToken;
import bqminh.e_commerce.enity.Role;
import bqminh.e_commerce.enity.User;
import bqminh.e_commerce.mapper.UserMapper;
import bqminh.e_commerce.repository.RefreshTokenRepository;
import bqminh.e_commerce.repository.RoleRepository;
import bqminh.e_commerce.repository.UserRepository;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    @Value("${jwt.secret}")
    private String secret ;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository,RefreshTokenRepository refreshTokenRepository,UserMapper userMapper,PasswordEncoder passwordEncoder,RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenRepository=refreshTokenRepository;
        this.userMapper=userMapper;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
    }
    public void register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email da ton tai");
        }
        if (userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username da ton tai");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new RuntimeException("Pass khong trung nhau");
        }
        User user=userMapper.toUser(request);
        Role setRoleUser=roleRepository.findByName("USER");
        user.setRole(setRoleUser);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
         userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
        Authentication authentication= authenticationManager.authenticate(token);
        User user=userRepository.findByEmail(request.getEmail());
        String accessToken= jwtService.createAccessToken(user);
        String refreshToken= jwtService.createRefreshToken(user);
        RefreshToken refreshTokenDB= refreshTokenRepository.findByUser(user);
        if (refreshTokenDB!=null){
            refreshTokenDB.setRefreshToken(refreshToken);
            refreshTokenDB.setExpiryDate(new Date(System.currentTimeMillis()+refreshExpiration));
            refreshTokenRepository.save(refreshTokenDB);
        }else {
            RefreshToken newRefreshToken=new RefreshToken();
            newRefreshToken.setUser(user);
            newRefreshToken.setRefreshToken(refreshToken);
            newRefreshToken.setExpiryDate(new Date(System.currentTimeMillis()+refreshExpiration));
            refreshTokenRepository.save(newRefreshToken);
        }
        return new LoginResponse(accessToken,refreshToken);
    }
    public void logout(String refreshToken){
        RefreshToken refreshTokenDB=refreshTokenRepository.findByRefreshToken(refreshToken);
        if (refreshTokenDB!=null){
            refreshTokenRepository.delete(refreshTokenDB);
        }else {
            throw new RuntimeException("RefreshToke ko ton tai");
        }
    }
    public JWTClaimsSet parseToken(String refreshToken) {
        try {
            //  Giải mã JWT: Header + Payload + Signature
            JWSObject jwsObject = JWSObject.parse(refreshToken);
            //Xác thực chữ ký của refresh token
            JWSVerifier verifier = new MACVerifier(secret.getBytes());
            if (!jwsObject.verify(verifier)) {
                throw new RuntimeException("Chữ ký token không hợp lệ");
            }
            //  Lấy Payload (phần dữ liệu)
            return JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        } catch (Exception e) {
            throw new RuntimeException("Token không hợp lệ", e);
        }
    }
    public String refreshAccessToken(String refreshToken) {
        // 1. Decode token
        JWTClaimsSet jwtClaimsSet = parseToken(refreshToken);
        // 2. Kiểm tra type
        String type = (String) jwtClaimsSet.getClaim("type");
        if (!"refresh".equals(type)) {
            throw new RuntimeException("Token không phải loại refresh");
        }
        // 3. Kiểm tra thời hạn
        Date exp = jwtClaimsSet.getExpirationTime();
        if (exp.before(new Date())) {
            throw new RuntimeException("Refresh token đã hết hạn");
        }
        // 4. Lấy user từ subject
        String email = jwtClaimsSet.getSubject();
        User user = userRepository.findByEmail(email);
        // 5. Kiểm tra token có trong DB không
        RefreshToken tokenInDb = refreshTokenRepository.findByRefreshToken(refreshToken);
        if (tokenInDb == null) {
            throw new RuntimeException("Refresh token không tồn tại");
        }
        // 6. Tạo access token mới
        return jwtService.createAccessToken(user);
    }
}
