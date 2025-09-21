package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.request.UserRequest;
import bqminh.e_commerce.dto.request.UserUpdate;
import bqminh.e_commerce.dto.response.UserResponse;
import bqminh.e_commerce.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserResponse createUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }
    @GetMapping("/users")
    public List<UserResponse>getAllUser(){
        return userService.getAllUser();
    }
    @GetMapping("/users/{id}")
    private UserResponse getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }
    @PutMapping("/users")
    public UserResponse updateUser(@RequestBody UserUpdate update){
        return userService.updateUser(update);
    }
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable long id){
        return userService.deleteUser(id);
    }
}
