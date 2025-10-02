package bqminh.e_commerce.repository;

import bqminh.e_commerce.enity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByName(String name);
}
