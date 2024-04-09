package com.pe.indra.product.infrastructure.persistence;

import com.pe.indra.product.infrastructure.persistence.entity.MongoProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoProductEntityRepository extends ReactiveMongoRepository<MongoProductEntity, String> {
}
