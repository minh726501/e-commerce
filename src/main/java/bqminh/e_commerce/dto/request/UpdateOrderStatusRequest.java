package bqminh.e_commerce.dto.request;

import bqminh.e_commerce.enity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
