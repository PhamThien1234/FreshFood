package com.example.FreshFood.mapper;

import com.example.FreshFood.dto.response.ProductResponse;
import com.example.FreshFood.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "farmer.id", target = "farmerId")
    @Mapping(source = "farmer.fullName", target = "farmerName")
    ProductResponse toProductResponse(Product product);
}
