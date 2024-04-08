package com.pe.indra.product.infrastructure.persistence;
import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.domain.repository.ProductRepository;
import com.pe.indra.product.infrastructure.persistence.mapper.ProductEntityMapper;
import com.pe.indra.product.infrastructure.persistence.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@Slf4j
@RequiredArgsConstructor
public class MongoProductRepositoryImpl implements ProductRepository {
    private final ProductEntityMapper productEntityMapper;

    private final ProductEntityRepository productEntityRepository;

    @Override
    public Flux<Product> listProduct() {
        return productEntityRepository.findAll()
                .map(productEntityMapper::mapProductEntityToProduct);
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return productEntityRepository.findById(id)
                .map(productEntityMapper::mapProductEntityToProduct);
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        product.setProductId(null);
        return productEntityRepository.save(productEntityMapper.mapProductToProductEntity(product))
                .map(productEntityMapper::mapProductEntityToProduct);
    }

    @Override
    public Mono<Product> updateProduct(String id, Product product) {
        return productEntityRepository.findById(id)
                .flatMap(existingProductEntity -> {
                    Product updatedProduct = productEntityMapper.mapProductEntityToProduct(existingProductEntity);
                    Utils.copyNonNullProperties(product,updatedProduct);
                    return productEntityRepository.save(productEntityMapper.mapProductToProductEntity(updatedProduct));
                })
                .map(productEntityMapper::mapProductEntityToProduct);
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productEntityRepository.deleteById(id);
    }
}
