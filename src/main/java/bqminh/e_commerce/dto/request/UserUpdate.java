package bqminh.e_commerce.dto.request;

import bqminh.e_commerce.enity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdate {
    private Long id;
    @Email(message = "Email không hợp lệ")
    private String email;
    @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
    private String password;
}
