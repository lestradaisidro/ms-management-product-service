package com.pe.indra.product.domain.usercases;
import com.pe.indra.product.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {

        Flux<Product> listProduct(String db);

        Mono<Product> getProductById(String id,String db);

        Mono<Product> createProduct(Product product,String db);

        Mono<Product> updateProduct(String id,String db, Product product);

        Mono<Void> deleteProduct(String id,String db);
}
