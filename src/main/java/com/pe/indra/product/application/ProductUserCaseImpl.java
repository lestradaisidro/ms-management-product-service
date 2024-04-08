package com.pe.indra.product.application;
import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.domain.repository.ProductRepository;
import com.pe.indra.product.domain.usercases.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductUserCaseImpl implements ProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Flux<Product> listProduct(String db) {
        return productRepository.listProduct();

    }

    @Override
    public Mono<Product> getProductById(String id,String db) {
        return productRepository.getProductById(id);
    }

    @Override
    public Mono<Product> createProduct(Product product,String db) {
        return productRepository.createProduct(product);
    }

    @Override
    public Mono<Product> updateProduct(String id,String db, Product product) {
        return productRepository.updateProduct(id, product);
    }

    @Override
    public Mono<Void> deleteProduct(String id,String db) {
        return productRepository.deleteProduct(id);
    }
}
