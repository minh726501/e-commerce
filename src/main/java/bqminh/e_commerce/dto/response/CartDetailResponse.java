package bqminh.e_commerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailResponse {
    private String productName;
    private double price;
    private int quantity;
    private double subTotal;
}
