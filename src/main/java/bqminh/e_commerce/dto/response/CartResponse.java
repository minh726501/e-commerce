package bqminh.e_commerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CartResponse {
    private String username;
    private int totalItem;
    private double totalPrice;
    private List<CartDetailResponse> cartDetails;
}
