package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.ApiResponse;
import bqminh.e_commerce.enity.Role;
import bqminh.e_commerce.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping("/roles")
    private ResponseEntity<ApiResponse<Role>>createRole(@RequestBody Role role){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "create Role success",roleService.createRole(role)));
    }
}
