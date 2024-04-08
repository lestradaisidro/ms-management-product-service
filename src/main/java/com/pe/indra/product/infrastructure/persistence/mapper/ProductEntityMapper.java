package com.pe.indra.product.infrastructure.persistence.mapper;


import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {


    @Mapping(target = "id", source = "productId")
    ProductEntity mapProductToProductEntity(Product product);

    @Mapping(target = "productId", source = "id")
    Product mapProductEntityToProduct(ProductEntity productEntity);
}
