package com.example.productmanagement.service;

import com.example.productmanagement.dto.ProductRequest;
import com.example.productmanagement.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
}
