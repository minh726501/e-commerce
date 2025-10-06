package bqminh.e_commerce.dto.response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private double totalPrice;
    private String status;
    private List<OrderDetailResponse> orderDetails;
}
