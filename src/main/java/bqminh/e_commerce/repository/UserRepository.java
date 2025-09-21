package bqminh.e_commerce.repository;


import bqminh.e_commerce.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
