package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.ApiResponse;
import bqminh.e_commerce.dto.request.CreateOrderRequest;
import bqminh.e_commerce.dto.request.UpdateOrderStatusRequest;
import bqminh.e_commerce.dto.response.OrderResponse;
import bqminh.e_commerce.enity.enums.OrderStatus;
import bqminh.e_commerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/orders/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>>checkout(@RequestBody CreateOrderRequest request){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Thanh toan thanh cong", orderService.checkout(request)));
    }
    @GetMapping("/orders/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>>getOrderByUseId(@PathVariable long userId){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "get Order By UserId", orderService.getOrderByUserId(userId)));
    }
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>>updateOrderStatus(@PathVariable long orderId,@RequestBody UpdateOrderStatusRequest status){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Cap nhat trang thai don hang thanh cong",orderService.updateOrderStatus(status,orderId)));
    }
}
