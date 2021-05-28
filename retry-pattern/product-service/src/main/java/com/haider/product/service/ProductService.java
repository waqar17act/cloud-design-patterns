package com.haider.product.service;

import com.haider.dto.ProductDto;
import com.haider.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.CompletionStage;

@Service
public class ProductService {

    private Map<Integer, Product> map;

    @Autowired
    private RatingServiceClient ratingServiceClient;

    @PostConstruct
    private void init(){
        this.map = Map.of(
                1, Product.of(1, "Pepsi", 60.00),
                2, Product.of(2, "Sprite", 65.00)
        );
    }

    public CompletionStage<ProductDto> getProductDto(int productId){
           return this.ratingServiceClient.getProductRatingDto(1)
                   .thenApply(productRatingDto -> {
                       Product product = this.map.get(productId);
                       return ProductDto.of(productId, product.getDescription(), product.getPrice(), productRatingDto);
                   });
    }

}
