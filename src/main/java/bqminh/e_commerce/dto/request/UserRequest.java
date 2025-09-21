package bqminh.e_commerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
