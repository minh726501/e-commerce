package bqminh.e_commerce.mapper;

import bqminh.e_commerce.dto.request.ProductRequest;
import bqminh.e_commerce.dto.request.ProductUpdateRequest;
import bqminh.e_commerce.dto.response.ProductResponse;
import bqminh.e_commerce.enity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", source = "category.name")
    ProductResponse toProductResponse(Product product);
    Product toProduct(ProductRequest request);
    List<ProductResponse>toListProductResponse(List<Product>products);
    void updateProduct(ProductUpdateRequest request, @MappingTarget Product product);
}
