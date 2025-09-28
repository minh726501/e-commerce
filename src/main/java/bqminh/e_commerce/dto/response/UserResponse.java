package bqminh.e_commerce.dto.response;

import bqminh.e_commerce.enity.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String username;
    private String email;
    private String role;
}
