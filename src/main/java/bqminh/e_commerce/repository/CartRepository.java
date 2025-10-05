package bqminh.e_commerce.repository;

import bqminh.e_commerce.enity.Cart;
import bqminh.e_commerce.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}