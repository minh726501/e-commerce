package bqminh.e_commerce.repository;

import bqminh.e_commerce.enity.Order;
import bqminh.e_commerce.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<List<Order>>findAllByUser(User user);
}
