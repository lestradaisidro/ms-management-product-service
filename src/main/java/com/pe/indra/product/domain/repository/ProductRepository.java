package com.pe.indra.product.domain.repository;


import com.pe.indra.product.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Flux<Product> listProduct();
    Mono<Product> getProductById(String id);
    Mono<Product> createProduct(Product product) ;
    Mono<Product> updateProduct(String id, Product product);
    Mono<Void> deleteProduct(String id);
}
