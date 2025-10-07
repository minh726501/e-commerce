package bqminh.e_commerce.service;

import bqminh.e_commerce.dto.request.ProductRequest;
import bqminh.e_commerce.dto.request.ProductUpdateRequest;
import bqminh.e_commerce.dto.response.PagedResponse;
import bqminh.e_commerce.dto.response.ProductResponse;
import bqminh.e_commerce.enity.Category;
import bqminh.e_commerce.enity.Product;
import bqminh.e_commerce.mapper.ProductMapper;
import bqminh.e_commerce.repository.CategoryRepository;
import bqminh.e_commerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,ProductMapper productMapper,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper=productMapper;
        this.categoryRepository=categoryRepository;
    }

    public ProductResponse createProduct(ProductRequest request){
        Optional<Category>getCategory=categoryRepository.findById(request.getCategoryId());
        if (getCategory.isEmpty()){
            throw new RuntimeException("Category not found");
        }
        if (productRepository.existsByName(request.getName())){
            throw new RuntimeException("Product da ton tai");
        }
        Product newProduct=productMapper.toProduct(request);
        newProduct.setCategory(getCategory.get());
        productRepository.save(newProduct);
        return productMapper.toProductResponse(newProduct);
    }
    public ProductResponse getProductById(long id){
        Optional<Product>getProductDB=productRepository.findById(id);
        if (getProductDB.isEmpty()){
            throw new RuntimeException("Khong ton tai Product voi Id : "+id);
        }
        return productMapper.toProductResponse(getProductDB.get());
    }
    public PagedResponse<ProductResponse> getAllProduct(int page,int size){
        Pageable pageable= PageRequest.of(page-1,size);
        Page<Product>productPage=productRepository.findAll(pageable);
        List<Product>productList=productPage.getContent();
        List<ProductResponse>content=new ArrayList<>();
        for (Product product:productList){
            ProductResponse response=productMapper.toProductResponse(product);
            content.add(response);
        }
        return new PagedResponse<>(content,productPage.getNumber()+1,productPage.getSize(),productPage.getTotalPages(),productPage.getTotalElements());
    }
    public PagedResponse<ProductResponse>filterProducts(String category,String keyword,Double minPrice,Double maxPrice,int page,int size){
        int offset=(page-1)*size;
        List<Product>filterProduct=productRepository.filterProduct(category,keyword,minPrice,maxPrice,size,offset);
        long totalElements = productRepository.countFilteredProducts(category, keyword, minPrice, maxPrice);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new PagedResponse<>(productMapper.toListProductResponse(filterProduct),page,size,totalPages,totalElements);
    }

    public ProductResponse updateProduct(ProductUpdateRequest request){
        Optional<Category>getCategoryDB=categoryRepository.findById(request.getCategoryId());
        if (getCategoryDB.isEmpty()){
            throw new RuntimeException("ko co danh muc ton tai voi id : "+request.getCategoryId());
        }
        Optional<Product>getProduct=productRepository.findById(request.getId());
        if (getProduct.isEmpty()){
            throw new RuntimeException("Khong ton tai Product voi Id : "+request.getId());
        }
        if (productRepository.existsByName(request.getName()) && !getProduct.get().getName().equals(request.getName())){
            throw new RuntimeException("Product da ton tai");
        }
        Product saveProduct=getProduct.get();
        productMapper.updateProduct(request,saveProduct);
        saveProduct.setCategory(getCategoryDB.get());
        productRepository.save(saveProduct);
        return productMapper.toProductResponse(getProduct.get());
    }
    public void deleteProductById(long id){
        Optional<Product>getProductDB=productRepository.findById(id);
        if (getProductDB.isEmpty()){
            throw new RuntimeException("Khong ton tai Product voi Id : "+id);
        }
        productRepository.deleteById(id);
    }
}
