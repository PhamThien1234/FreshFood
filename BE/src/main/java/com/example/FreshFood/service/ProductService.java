package com.example.FreshFood.service;

import com.example.FreshFood.dto.request.ProductRequest;
import com.example.FreshFood.dto.request.ProductUpdateRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class    ProductService {
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final ProductMapper productMapper;

    private final FileStorageService fileStorageService;

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
    public List<ProductResponse> getApprovedProducts(){
        List<Product> products = productRepository.findByStatus(ProductStatus.APPROVED);

        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
    public List<ProductResponse> searchProducts(String keyword){
        List<Product> products = productRepository.findByStatusAndNameContainingIgnoreCase(ProductStatus.APPROVED, keyword);

        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
    public List<ProductResponse> filterProducts(String category){
        List<Product> products = productRepository.findByStatusAndCategoryIgnoreCase(ProductStatus.APPROVED, category);

        return products.stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
    public ProductResponse updateProduct(UUID id, ProductUpdateRequest request, String username){
        Product product = productRepository.findByIdAndFarmerUsername(id, username)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategory(request.getCategory());

        product.setStatus(ProductStatus.PENDING);
        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }
    public void deleteProduct(UUID id, String username){
        Product product = productRepository.findByIdAndFarmerUsername(id, username)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }
    public ProductResponse uploadProductImage(
            UUID id,
            MultipartFile file,
            String username
    ) {
        Product product = productRepository
                .findByIdAndFarmerUsername(id, username)
                .orElseThrow(() ->
                        new AppException(ErrorCode.PRODUCT_NOT_FOUND)
                );
        String imageUrl = fileStorageService.saveProductImage(file);

        product.setImageUrl(imageUrl);

        product.setStatus(ProductStatus.PENDING);

        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }
}
