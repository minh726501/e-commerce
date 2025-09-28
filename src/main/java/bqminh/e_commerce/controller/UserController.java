package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.ApiResponse;
import bqminh.e_commerce.dto.request.UserRequest;
import bqminh.e_commerce.dto.request.UserUpdate;
import bqminh.e_commerce.dto.response.UserResponse;
import bqminh.e_commerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserRequest request){
        return ResponseEntity.ok(new ApiResponse<>(200,"Tao User Thanh Cong ",userService.createUser(request)));
    }
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>>getAllUser(){
        return ResponseEntity.ok(new ApiResponse<>(200,"Lay All User",userService.getAllUser()));
    }
    @GetMapping("/users/{id}")
    private ResponseEntity<ApiResponse<UserResponse>>getUserById(@PathVariable long id){
        return ResponseEntity.ok(new ApiResponse<>(200,"Lay User By Id",userService.getUserById(id)));
    }
    @PutMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@RequestBody @Valid UserUpdate update){
        return ResponseEntity.ok(new ApiResponse<>(200,"Update User",userService.updateUser(update)));
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
