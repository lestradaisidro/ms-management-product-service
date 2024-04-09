package com.pe.indra.product.infrastructure.persistence.mapper;


import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.infrastructure.persistence.entity.MongoProductEntity;
import com.pe.indra.product.infrastructure.persistence.entity.R2dbcProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {


    @Mapping(target = "id", source = "productId")
    MongoProductEntity mapProductToProductEntity(Product product);

    @Mapping(target = "productId", source = "id")
    Product mapProductEntityToProduct(MongoProductEntity productEntity);

    @Mapping(target = "id", source = "productId")
    R2dbcProductEntity mapProductToR2dbcProductEntity(Product product);

    @Mapping(target = "productId", source = "id")
    Product mapR2dbcProductEntityToProduct(R2dbcProductEntity productEntity);
}
