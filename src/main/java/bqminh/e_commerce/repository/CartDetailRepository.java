package bqminh.e_commerce.repository;

import bqminh.e_commerce.enity.Cart;
import bqminh.e_commerce.enity.CartDetail;
import bqminh.e_commerce.enity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    Optional<CartDetail> findByCartAndProduct(Cart cart, Product product);
}