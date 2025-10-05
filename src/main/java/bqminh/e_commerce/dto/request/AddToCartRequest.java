package bqminh.e_commerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddToCartRequest {
    private long userId;
    private long productId;
    private int quantity;

}
