package com.pe.indra.product.infrastructure.rest.mapper;


import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.infrastructure.rest.model.ProductRequest;
import com.pe.indra.product.infrastructure.rest.model.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductRestMapper {

    @Mapping(defaultValue = "0.0", target = "discount")
    Product mapProductRequestToProduct(ProductRequest productRequest);
    ProductResponse mapProductToProductResponse(Product product);
}
