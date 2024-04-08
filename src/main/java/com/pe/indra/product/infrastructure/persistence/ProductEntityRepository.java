package com.pe.indra.product.infrastructure.persistence;

import com.pe.indra.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductEntityRepository extends ReactiveMongoRepository<ProductEntity, String> {
}
