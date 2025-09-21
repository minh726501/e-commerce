package bqminh.e_commerce.service;

import bqminh.e_commerce.dto.request.UserRequest;
import bqminh.e_commerce.dto.request.UserUpdate;
import bqminh.e_commerce.dto.response.UserResponse;
import bqminh.e_commerce.enity.User;
import bqminh.e_commerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public UserResponse createUser(UserRequest request){
        User user=new User();
        UserResponse userResponse=new UserResponse();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        userResponse.setEmail(request.getEmail());
        userResponse.setUsername(request.getUsername());
        return userResponse;
    }
    public List<UserResponse>getAllUser(){
        List<UserResponse>responses=new ArrayList<>();
        List<User>users=userRepository.findAll();
        for (User user:users){
            UserResponse userResponse=new UserResponse();
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            responses.add(userResponse);
        }
        return responses;
    }
    public UserResponse getUserById(long id){
        Optional<User> getUser=userRepository.findById(id);
        if (getUser.isEmpty()){
            throw new RuntimeException("Ko co user ton tai voi id: "+id);
        }
        UserResponse userResponse=new UserResponse();
        userResponse.setEmail(getUser.get().getEmail());
        userResponse.setUsername(getUser.get().getUsername());
        return userResponse;
    }
    public UserResponse updateUser(UserUpdate update){
        Optional<User>getUser=userRepository.findById(update.getId());
        if (getUser.isEmpty()){
            throw new RuntimeException("Ko co user ton tai voi id: "+update.getId());
        }
        User existingUser=getUser.get();
        existingUser.setEmail(update.getEmail());
        existingUser.setPassword(update.getPassword());
        userRepository.save(existingUser);
        UserResponse response=new UserResponse();
        response.setUsername(existingUser.getUsername());
        response.setEmail(existingUser.getEmail());
        return response;
    }
    public String deleteUser(long id){
        Optional<User>getUser=userRepository.findById(id);
        if (getUser.isEmpty()){
            throw new RuntimeException("Ko co user ton tai voi id: "+id);
        }
        userRepository.deleteById(id);
        return "da xoa User voi id: "+id;
    }
}
