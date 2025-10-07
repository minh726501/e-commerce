package bqminh.e_commerce.repository;

import bqminh.e_commerce.enity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = """
    SELECT p.* 
    FROM products p 
    JOIN categories c ON p.category_id = c.id 
    WHERE (:category IS NULL OR c.name LIKE CONCAT('%', :category, '%'))
      AND (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
      AND (:minPrice IS NULL OR p.price >= :minPrice)
      AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    ORDER BY p.id
    LIMIT :size OFFSET :offset
""", nativeQuery = true)
    List<Product>filterProduct(@Param("category")String category,
                               @Param("keyword")String keyword,
                               @Param("minPrice")Double minPrice,
                               @Param("maxPrice")Double maxPrice,
                               @Param("size") int size,
                               @Param("offset")int offset);
    @Query(value = """
        SELECT COUNT(*) 
        FROM products p
        JOIN categories c ON p.category_id = c.id
        WHERE (:category IS NULL OR c.name LIKE CONCAT('%', :category, '%'))
          AND (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    """, nativeQuery = true)
    long countFilteredProducts(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
    boolean existsByName(String name);
}
