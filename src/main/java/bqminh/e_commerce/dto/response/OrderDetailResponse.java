package bqminh.e_commerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailResponse {
    private String productName;
    private int quantity;
    private double price;
    private double subTotal;
}
