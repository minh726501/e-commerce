package bqminh.e_commerce.mapper;

import bqminh.e_commerce.dto.request.CategoryRequest;
import bqminh.e_commerce.dto.request.CategoryUpdateRequest;
import bqminh.e_commerce.dto.response.CategoryResponse;
import bqminh.e_commerce.enity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse>toListCategoryResponse(List<Category>categories);
    void updateCategoryFromDto(CategoryUpdateRequest request, @MappingTarget Category category);
}
