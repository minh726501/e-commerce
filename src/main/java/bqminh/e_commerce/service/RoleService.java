package bqminh.e_commerce.service;

import bqminh.e_commerce.enity.Role;
import bqminh.e_commerce.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role createRole(Role role){
        if (roleRepository.existsByName(role.getName())){
            throw new RuntimeException("role da ton tai");
        }
        return roleRepository.save(role);
    }
}
