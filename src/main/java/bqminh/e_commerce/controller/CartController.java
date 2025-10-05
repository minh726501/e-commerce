package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.ApiResponse;
import bqminh.e_commerce.dto.request.AddToCartRequest;
import bqminh.e_commerce.dto.request.UpdateCartRequest;
import bqminh.e_commerce.dto.response.CartResponse;
import bqminh.e_commerce.enity.Cart;
import bqminh.e_commerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("/carts/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>>getCartByUserId(@PathVariable long userId){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Get Cart By UserId", cartService.getCartByUserId(userId)));
    }
    @PostMapping("/carts/add")
    public ResponseEntity<ApiResponse<CartResponse>>addToCart(@RequestBody AddToCartRequest request){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Add Product To cart", cartService.addToCart(request)));
    }
    @PostMapping("/carts/update")
    public ResponseEntity<ApiResponse<CartResponse>>updateCart(@RequestBody UpdateCartRequest request){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Update Cart", cartService.updateCart(request)));
    }
}
