package com.pe.indra.product.infrastructure.rest;

import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.domain.usercases.ProductUseCase;
import com.pe.indra.product.infrastructure.rest.mapper.ProductRestMapper;
import com.pe.indra.product.infrastructure.rest.model.ProductRequest;
import com.pe.indra.product.infrastructure.rest.model.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;
class ProductControllerTest {

    @Mock
    private ProductUseCase productUseCase;

    @Mock
    private ProductRestMapper productRestMapper;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productUseCase, productRestMapper);
    }

    @Test
    void create() {
        ProductRequest productRequest = new ProductRequest();
        Product product = new Product();
        String db = "mongo";
        ProductResponse productResponse = new ProductResponse();
        when(productRestMapper.mapProductRequestToProduct(productRequest)).thenReturn(product);
        when(productUseCase.createProduct(product, db)).thenReturn(Mono.just(product));
        when(productRestMapper.mapProductToProductResponse(product)).thenReturn(productResponse);
        Mono<ProductResponse> response = productController.create(productRequest, db).getBody();
        assert response != null;
        StepVerifier.create(response)
                .expectNext(productResponse)
                .verifyComplete();

        verify(productUseCase).createProduct(product, db);
        verify(productRestMapper).mapProductRequestToProduct(productRequest);
        verify(productRestMapper).mapProductToProductResponse(product);
    }

    @Test
    void update() {
        String id = "6613f8fdfa92b11489ab2dd7";
        String db = "mongo";
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(id);
        Product product = new Product();
        ProductResponse productResponse = new ProductResponse();

        when(productRestMapper.mapProductRequestToProduct(productRequest)).thenReturn(product);
        when(productUseCase.updateProduct(id, db, product)).thenReturn(Mono.just(product));
        when(productRestMapper.mapProductToProductResponse(product)).thenReturn(productResponse);

        Mono<ProductResponse> response = productController.update(id, productRequest, db).getBody();

        assert response != null;
        StepVerifier.create(response)
                .expectNext(productResponse)
                .verifyComplete();

        verify(productUseCase).updateProduct(id, db, product);
        verify(productRestMapper).mapProductRequestToProduct(productRequest);
        verify(productRestMapper).mapProductToProductResponse(product);
    }
    @Test
    void listAll() {
        String db = "mongo";
        Product product = new Product();
        ProductResponse productResponse = new ProductResponse();
        when(productUseCase.listProduct(db)).thenReturn(Flux.just(product));
        when(productRestMapper.mapProductToProductResponse(product)).thenReturn(productResponse);

        Flux<ProductResponse> response = productController.listAll(db).getBody();

        assert response != null;
        StepVerifier.create(response)
                .expectNext(productResponse)
                .verifyComplete();

        verify(productUseCase).listProduct(db);
        verify(productRestMapper).mapProductToProductResponse(product);
    }
    @Test
    void getById() {
        String id = "6613f8fdfa92b11489ab2dd7";
        String db = "mongodb";
        Product product = new Product();
        ProductResponse productResponse = new ProductResponse();
        when(productUseCase.getProductById(id, db)).thenReturn(Mono.just(product));
        when(productRestMapper.mapProductToProductResponse(product)).thenReturn(productResponse);
        Mono<ProductResponse> response = productController.getById(id, db).getBody();
        assert response != null;
        StepVerifier.create(response)
                .expectNext(productResponse)
                .verifyComplete();

        verify(productUseCase).getProductById(id, db);
        verify(productRestMapper).mapProductToProductResponse(product);
    }
    @Test
    void delete() {
        String id = "1";
        String db = "r2dbc";
        when(productUseCase.deleteProduct(id, db)).thenReturn(Mono.empty());
        Mono<Void> response = productController.delete(id, db).getBody();
        assert response != null;
        StepVerifier.create(response)
                .verifyComplete();

        verify(productUseCase).deleteProduct(id, db);
    }
}