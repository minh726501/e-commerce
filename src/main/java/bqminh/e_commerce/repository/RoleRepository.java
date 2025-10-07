package bqminh.e_commerce.repository;

import bqminh.e_commerce.enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    boolean existsByName(String name);
    Role findByName(String roleName);
}
