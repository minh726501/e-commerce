package bqminh.e_commerce.service;

import bqminh.e_commerce.dto.request.CategoryRequest;
import bqminh.e_commerce.dto.request.CategoryUpdateRequest;
import bqminh.e_commerce.dto.response.CategoryResponse;
import bqminh.e_commerce.enity.Category;
import bqminh.e_commerce.mapper.CategoryMapper;
import bqminh.e_commerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository,CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper=categoryMapper;
    }

    public CategoryResponse createCategory(CategoryRequest request){
        if (categoryRepository.existsByName(request.getName())){
            throw new RuntimeException("Ten danh muc da ton tai");
        }
        Category category=categoryMapper.toCategory(request);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }
    public CategoryResponse getCategoryById(long id){
        Optional<Category>categoryDB=categoryRepository.findById(id);
        if (categoryDB.isEmpty()){
            throw new RuntimeException("ko co danh muc voi id "+id);
        }
        Category category=categoryDB.get();
        return categoryMapper.toCategoryResponse(category);
    }
    public List<CategoryResponse>getAllCategory(){
        List<Category>getListCategory=categoryRepository.findAll();
        return categoryMapper.toListCategoryResponse(getListCategory);
    }
    public CategoryResponse updateCategory(CategoryUpdateRequest request){
        Optional<Category>getCategoryById=categoryRepository.findById(request.getId());
        if (getCategoryById.isEmpty()){
            throw new RuntimeException("ko co danh muc ton tai voi id : "+request.getId());
        }
        Category getCategory=getCategoryById.get();
        categoryMapper.updateCategoryFromDto(request,getCategory);
        categoryRepository.save(getCategory);
        return categoryMapper.toCategoryResponse(getCategory);
    }
    public void deleteCategory(long id){
        Optional<Category> getCategory=categoryRepository.findById(id);
        if (getCategory.isEmpty()){
            throw new RuntimeException("ko ton tai danh muc voi id : "+id);
        }
        categoryRepository.deleteById(id);
    }
}
