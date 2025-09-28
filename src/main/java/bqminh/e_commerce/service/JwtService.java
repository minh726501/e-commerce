package bqminh.e_commerce.service;

import bqminh.e_commerce.enity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret ;
    @Value("${jwt.expiration}")
    private long expiration ;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public String createAccessToken(User user) {
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .claim("role",user.getRole().getName())
                    .issuer("e-commerce")
                    .issueTime(new Date(System.currentTimeMillis()))
                    .expirationTime(new Date(System.currentTimeMillis() + expiration))
                    .build();
            SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
            signedJWT.sign(new MACSigner(secret.getBytes()));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to sign JWT", e);
        }
    }
    public String createRefreshToken(User user){
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .issuer("e-commerce")
                    .claim("type","refresh")
                    .issueTime(new Date(System.currentTimeMillis()))
                    .expirationTime(new Date(System.currentTimeMillis() + refreshExpiration))
                    .build();
            SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
            signedJWT.sign(new MACSigner(secret.getBytes()));
            return signedJWT.serialize();
        }catch (JOSEException e){
            throw new RuntimeException("Failed to sign JWT", e);
        }
    }
}
