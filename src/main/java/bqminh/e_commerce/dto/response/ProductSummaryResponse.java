package bqminh.e_commerce.dto.response;

import lombok.Getter;

@Getter
public class ProductSummaryResponse {
    private String name;
    private double price;
    private int stock;
}
