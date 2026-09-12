package com.example.FreshFood.controller;

import com.example.FreshFood.dto.request.ProductRequest;
import com.example.FreshFood.dto.request.ProductUpdateRequest;
import com.example.FreshFood.dto.response.ProductResponse;
import com.example.FreshFood.entity.Product;
import com.example.FreshFood.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public List<ProductResponse> getApprovedProducts(){
        return productService.getApprovedProducts();
    }
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('PRODUCT_SEARCH')")
    public List<ProductResponse> searchProducts(@RequestParam String keyword){
        return productService.searchProducts(keyword);
    }
    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('PRODUCT_FILTER')")
    public List<ProductResponse> filterProducts(@RequestParam String category){
        return productService.filterProducts(category);
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
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ProductResponse updateProduct(@PathVariable UUID id,@Valid @RequestBody ProductUpdateRequest request, Authentication authentication){
        return productService.updateProduct(id, request,authentication.getName());
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public void deleteProduct(@PathVariable UUID id, Authentication authentication){
        productService.deleteProduct(id,authentication.getName());
    }
    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('PRODUCT_IMAGE_UPLOAD')")
    public ProductResponse uploadProductImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file,
                                              Authentication authentication) {
        return productService.uploadProductImage(
                id,
                file,
                authentication.getName()
        );
    }

}
