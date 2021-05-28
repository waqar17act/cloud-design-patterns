package com.haider.product.controller;

import com.haider.dto.ProductDto;
import com.haider.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("product/{productId}")
    public ProductDto getProduct(@PathVariable int productId){
        return this.productService.getProductDto(productId);
    }

    @GetMapping("products")
    public List<ProductDto> getAllProducts() throws InterruptedException {
        Thread.sleep(50);
        return this.productService.getAllProducts();
    }

}
