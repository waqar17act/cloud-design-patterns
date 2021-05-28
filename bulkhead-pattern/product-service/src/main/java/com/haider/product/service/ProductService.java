package com.haider.product.service;

import com.haider.dto.ProductDto;
import com.haider.dto.ProductRatingDto;
import com.haider.product.entity.Product;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private Map<Integer, Product> map;

    @Autowired
    private RatingServiceClient ratingServiceClient;

    @PostConstruct
    private void init(){
        this.map = Map.of(
                1, Product.of(1, "Coke", 12.45),
                2, Product.of(2, "7up", 12.12)
        );
    }

    public ProductDto getProductDto(int productId){
        ProductRatingDto ratingDto = this.ratingServiceClient.getProductRatingDto(1);
        Product product = this.map.get(productId);
        return ProductDto.of(productId, product.getDescription(), product.getPrice(), ratingDto);
    }

    public List<ProductDto> getAllProducts(){
        return this.map.values()
                .stream()
                .map(product -> ProductDto.of(product.getProductId(), product.getDescription(), product.getPrice(), ProductRatingDto.of(0, Collections.emptyList())))
                .collect(Collectors.toList());
    }

}
