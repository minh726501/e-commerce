package bqminh.e_commerce.service;

import bqminh.e_commerce.dto.request.UserRequest;
import bqminh.e_commerce.dto.request.UserUpdate;
import bqminh.e_commerce.dto.response.PagedResponse;
import bqminh.e_commerce.dto.response.UserResponse;
import bqminh.e_commerce.enity.User;
import bqminh.e_commerce.mapper.UserMapper;
import bqminh.e_commerce.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository repository,UserMapper userMapper,PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.userMapper=userMapper;
        this.passwordEncoder=passwordEncoder;
    }

    public UserResponse createUser(UserRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("username da ton tai");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("email da ton tai");
        }
        User user=userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
    public PagedResponse<UserResponse> getAllUser(int page, int size){
        Pageable pageable=PageRequest.of(page-1,size);
        Page<User>userPage=userRepository.findAll(pageable);
        List<User>userList=userPage.getContent();
        List<UserResponse>content=new ArrayList<>();
        for (User user:userList){
            UserResponse response=userMapper.toUserResponse(user);
            content.add(response);
        }
        return new PagedResponse<>(content,userPage.getNumber()+1,userPage.getSize(),userPage.getTotalPages(),userPage.getTotalElements());

    }
    public UserResponse getUserById(long id){
        Optional<User> getUser=userRepository.findById(id);
        if (getUser.isEmpty()){
            throw new RuntimeException("Ko co user ton tai voi id: "+id);
        }
        return userMapper.toUserResponse(getUser.get());
    }
    public UserResponse updateUser(UserUpdate update){
        Optional<User>getUser=userRepository.findById(update.getId());
        if (getUser.isEmpty()){
            throw new RuntimeException("Ko co user ton tai voi id: "+update.getId());
        }
        User existingUser= getUser.get();
        if (userRepository.existsByEmail(update.getEmail()) && !existingUser.getEmail().equals(update.getEmail())){
            throw new RuntimeException("email da ton tai");
        }
        userMapper.updateUserFromDto(update,existingUser);
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        userRepository.save(existingUser);
        return userMapper.toUserResponse(existingUser);
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
