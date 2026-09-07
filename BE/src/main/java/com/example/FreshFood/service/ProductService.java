package com.example.FreshFood.service;

import com.example.FreshFood.dto.request.ProductRequest;
import com.example.FreshFood.dto.response.ProductResponse;
import com.example.FreshFood.entity.Product;
import com.example.FreshFood.entity.User;
import com.example.FreshFood.enums.ProductStatus;
import com.example.FreshFood.exception.AppException;
import com.example.FreshFood.exception.ErrorCode;
import com.example.FreshFood.mapper.ProductMapper;
import com.example.FreshFood.repository.ProductRepository;
import com.example.FreshFood.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class    ProductService {
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request, String username){
        User farmer = userRepository.findByUsername(username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_UN_EXISTED));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .status(ProductStatus.PENDING)
                .farmer(farmer)
                .build();
        productRepository.save(product);

        return productMapper.toProductResponse(product);
    }
    public ProductResponse getProductById(UUID id){
        Product product = productRepository.findByIdAndStatus(id, ProductStatus.APPROVED)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toProductResponse(product);
    }
    public ProductResponse getApprovedProductById(UUID id){
        Product product = productRepository .findByIdAndStatus(id, ProductStatus.APPROVED)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toProductResponse(product);
    }
    public ProductResponse approveProduct(UUID id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (product.getStatus() != ProductStatus.PENDING) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_PROCESSED);
        }

        product.setStatus(ProductStatus.APPROVED);

        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }
    public List<ProductResponse> getProductByStatus(){
        List<Product> products = productRepository.findByStatus(ProductStatus.APPROVED);

        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
}
