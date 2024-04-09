package com.pe.indra.product.infrastructure.persistence;
import com.pe.indra.product.infrastructure.persistence.entity.R2dbcProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface R2dbcProductEntityRepository extends ReactiveCrudRepository<R2dbcProductEntity, Long> {
}
