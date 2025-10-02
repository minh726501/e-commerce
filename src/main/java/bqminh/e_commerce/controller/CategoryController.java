package bqminh.e_commerce.controller;

import bqminh.e_commerce.dto.ApiResponse;
import bqminh.e_commerce.dto.request.CategoryRequest;
import bqminh.e_commerce.dto.request.CategoryUpdateRequest;
import bqminh.e_commerce.dto.response.CategoryResponse;
import bqminh.e_commerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>>createCategory(@RequestBody CategoryRequest request){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Create Category Success", categoryService.createCategory(request)));
    }
    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>>getCategoryById(@PathVariable long id){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Get Category By Id : "+id, categoryService.getCategoryById(id)));
    }
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>>getListCategory(){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Get List Category",categoryService.getAllCategory()));
    }
    @PutMapping("/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>>updateCategory(@RequestBody CategoryUpdateRequest request){
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Update Category Success", categoryService.updateCategory(request)));
    }
    @DeleteMapping("/categories")
    public ResponseEntity<ApiResponse<Void>>deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "delete category success",null));
    }
}
