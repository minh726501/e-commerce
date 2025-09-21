package bqminh.e_commerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdate {
    private Long id;
    private String email;
    private String password;
}
