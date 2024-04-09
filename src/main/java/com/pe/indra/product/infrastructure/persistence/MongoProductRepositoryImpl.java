package com.pe.indra.product.infrastructure.persistence;
import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.domain.repository.ProductRepository;
import com.pe.indra.product.exception.RepositoryException;
import com.pe.indra.product.infrastructure.persistence.mapper.ProductEntityMapper;
import com.pe.indra.product.infrastructure.persistence.util.Utils;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.pe.indra.product.infrastructure.persistence.util.Constants.ERROR_CONEXION_MONGO_DB;


@Component
@Slf4j
public class MongoProductRepositoryImpl implements ProductRepository {

    private final CircuitBreaker circuitBreaker;
    private final Retry retry;
    private final ProductEntityMapper productEntityMapper;
    private final MongoProductEntityRepository productEntityRepository;
    private static final String CIRCUIT_BREAKER_CONFIG_NAME = "productService";
    private static final String RETRY_CONFIG_NAME = "productService";

    public MongoProductRepositoryImpl(final CircuitBreakerRegistry circuitBreakerRegistry, final RetryRegistry retryRegistry, ProductEntityMapper productEntityMapper, MongoProductEntityRepository productEntityRepository) {
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_CONFIG_NAME);;
        this.retry = retryRegistry.retry(RETRY_CONFIG_NAME);;
        this.productEntityMapper = productEntityMapper;
        this.productEntityRepository = productEntityRepository;
    }


    @Override
    public Flux<Product> listProduct() {
        return productEntityRepository.findAll()
                .map(productEntityMapper::mapProductEntityToProduct)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(this::fallbackGenericFlux);
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return productEntityRepository.findById(id)
                .map(productEntityMapper::mapProductEntityToProduct)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(this::fallbackGenericMono);

    }

    @Override
    public Mono<Product> createProduct(Product product) {
        product.setProductId(null);
        return productEntityRepository.save(productEntityMapper.mapProductToProductEntity(product))
                .map(productEntityMapper::mapProductEntityToProduct)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(this::fallbackGenericMono);
    }

    @Override
    public Mono<Product> updateProduct(String id, Product product) {
        return productEntityRepository.findById(id)
                .flatMap(existingProductEntity -> {
                    Product updatedProduct = productEntityMapper.mapProductEntityToProduct(existingProductEntity);
                    Utils.copyNonNullProperties(product,updatedProduct);
                    return productEntityRepository.save(productEntityMapper.mapProductToProductEntity(updatedProduct));
                })
                .map(productEntityMapper::mapProductEntityToProduct)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(this::fallbackGenericMono);
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productEntityRepository.deleteById(id)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(this::fallbackGenericMonoVoid);
    }


    private Flux<Product> fallbackGenericFlux(Throwable e) {
        log.error("Error en Dao", e);
            if(e instanceof DataAccessException){
                return Flux.error(new RepositoryException(ERROR_CONEXION_MONGO_DB, e, HttpStatus.INTERNAL_SERVER_ERROR));
            }
            return Flux.error(new RepositoryException("Error getting product by id", e, HttpStatus.INTERNAL_SERVER_ERROR));
    }
    private Mono<Void> fallbackGenericMonoVoid(Throwable e) {
        log.error("Error en Dao", e);
        if(e instanceof DataAccessException){
            return Mono.error(new RepositoryException(ERROR_CONEXION_MONGO_DB, e, HttpStatus.INTERNAL_SERVER_ERROR));
        }
        return Mono.error(new RepositoryException("Error getting product by id", e, HttpStatus.INTERNAL_SERVER_ERROR));
    }
    private Mono<Product> fallbackGenericMono(Throwable e) {
        log.error("Error en Dao", e);
        if(e instanceof DataAccessException){
            return Mono.error(new RepositoryException(ERROR_CONEXION_MONGO_DB, e, HttpStatus.INTERNAL_SERVER_ERROR));
        }
        return Mono.error(new RepositoryException("Error getting product by id", e, HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
