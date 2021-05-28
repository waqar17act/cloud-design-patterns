package com.haider.rating.service;

import com.haider.dto.ProductRatingDto;
import com.haider.dto.ReviewDto;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    private Map<Integer, ProductRatingDto> map;

    @PostConstruct
    private void init(){

        // product 1
        ProductRatingDto ratingDto1 = ProductRatingDto.of(4.2,
                List.of(
                        ReviewDto.of("Haider", "Ali", 1, 5, "excellent"),
                        ReviewDto.of("Hamid", "Ahmed", 1, 4, "good")
                )
        );

        // product 2
        ProductRatingDto ratingDto2 = ProductRatingDto.of(4.5,
                List.of(
                        ReviewDto.of("Ahmed", "Mannan", 2, 5, "best"),
                        ReviewDto.of("Atif", "Ahmed", 2, 3, "No Comments")
                )
        );

        // map as db
        this.map = Map.of(
                1, ratingDto1,
                2, ratingDto2
        );

    }

    public ProductRatingDto getRatingForProduct(int productId) {
        return this.map.getOrDefault(productId, new ProductRatingDto());
    }

}
