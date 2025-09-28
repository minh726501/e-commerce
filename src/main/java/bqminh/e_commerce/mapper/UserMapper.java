package bqminh.e_commerce.mapper;

import bqminh.e_commerce.dto.request.UserRequest;
import bqminh.e_commerce.dto.request.UserUpdate;
import bqminh.e_commerce.dto.response.UserResponse;
import bqminh.e_commerce.enity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.name", target = "role")
    UserResponse toUserResponse(User user);
    User toUser(UserRequest request);
    List<UserResponse> toListUserResponse(List<User>users);
    void updateUserFromDto(UserUpdate update, @MappingTarget User user);
}
