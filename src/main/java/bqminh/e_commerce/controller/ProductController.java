package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.ApiResponse;
import bqminh.e_commerce.dto.request.ProductRequest;
import bqminh.e_commerce.dto.request.ProductUpdateRequest;
import bqminh.e_commerce.dto.response.ProductResponse;
import bqminh.e_commerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponse>>createProduct(@RequestBody ProductRequest request){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "create Product success", productService.createProduct(request)));
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>>getProductById(@PathVariable long id){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"Get Product By Id" , productService.getProductById(id)));
    }
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>getAllProduct(){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"Get All Product" , productService.getAllProduct()));
    }
    @PutMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponse>>updateProduct(@RequestBody ProductUpdateRequest request){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"Update Product Success" , productService.updateProduct(request)));
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Void>>updateProduct(@PathVariable long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"Delete Product Success" , null));
    }



}
