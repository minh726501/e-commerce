package bqminh.e_commerce.repository;

import bqminh.e_commerce.enity.RefreshToken;
import bqminh.e_commerce.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findByUser(User user);
    RefreshToken findByRefreshToken(String refreshToken);
}
