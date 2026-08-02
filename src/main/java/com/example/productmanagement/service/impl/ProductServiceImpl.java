package com.example.productmanagement.service.impl;

import com.example.productmanagement.dto.ProductRequest;
import com.example.productmanagement.dto.ProductResponse;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.entity.Role;
import com.example.productmanagement.entity.User;
import com.example.productmanagement.exception.ResourceNotFoundException;
import com.example.productmanagement.exception.UnauthorizedException;
import com.example.productmanagement.repository.ProductRepository;
import com.example.productmanagement.repository.UserRepository;
import com.example.productmanagement.security.CustomUserDetails;
import com.example.productmanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ProductResponse mapToDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .userId(product.getUser().getId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        User currentUser = getCurrentUser();

        List<Product> products;
        if (currentUser.getRole() == Role.ADMIN) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByUserId(currentUser.getId());
        }

        return products.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        User currentUser = getCurrentUser();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (currentUser.getRole() != Role.ADMIN && !product.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You do not have permission to view this product");
        }

        return mapToDto(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        User currentUser = getCurrentUser();

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .user(currentUser)
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToDto(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        User currentUser = getCurrentUser();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (currentUser.getRole() != Role.ADMIN && !product.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You do not have permission to update this product");
        }

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        Product updatedProduct = productRepository.save(product);
        return mapToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        User currentUser = getCurrentUser();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (currentUser.getRole() != Role.ADMIN && !product.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You do not have permission to delete this product");
        }

        productRepository.delete(product);
    }
}
