package bqminh.e_commerce.repository;

import bqminh.e_commerce.enity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}