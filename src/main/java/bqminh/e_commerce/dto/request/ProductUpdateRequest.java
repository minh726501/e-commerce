package bqminh.e_commerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {
    private long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private long categoryId;
}
