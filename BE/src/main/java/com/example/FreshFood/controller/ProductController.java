package com.example.FreshFood.controller;

import com.example.FreshFood.dto.request.ProductRequest;
import com.example.FreshFood.dto.response.ProductResponse;
import com.example.FreshFood.entity.Product;
import com.example.FreshFood.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request, Authentication authentication){
        return productService.createProduct(request, authentication.getName());
    }
    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    public List<ProductResponse> getApproveProducts(){
        return productService.getApprovedProducts();
    }
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('PRODUCT_SEARCH')")
    public List<ProductResponse> searchProducts(@RequestParam String keyword){
        return productService.searchProducts(keyword);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_VIEW')")
    public ProductResponse getProduct(@Valid @PathVariable UUID id){
        return productService.getApprovedProductById(id);
    }
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('PRODUCT_APPROVE')")
    public ProductResponse approveProduct(@PathVariable UUID id) {
        return productService.approveProduct(id);
    }

}
