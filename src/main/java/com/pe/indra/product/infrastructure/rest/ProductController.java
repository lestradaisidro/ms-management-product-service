package com.pe.indra.product.infrastructure.rest;

import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.domain.usercases.ProductUseCase;
import com.pe.indra.product.infrastructure.rest.mapper.ProductRestMapper;
import com.pe.indra.product.infrastructure.rest.model.ProductRequest;
import com.pe.indra.product.infrastructure.rest.model.ProductResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@RestController
@RequestMapping("product/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;
    private final ProductRestMapper productRestMapper;

    @ApiResponse(responseCode = "201", description = "Product created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class)))
    @PostMapping
    ResponseEntity<Mono<ProductResponse>> create(@RequestBody ProductRequest productRequest,@RequestHeader(required = false) String db) {
        Mono<Product> result = productUseCase.createProduct(productRestMapper.mapProductRequestToProduct(productRequest), db);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result.map(productRestMapper::mapProductToProductResponse));
    }

    @ApiResponse(responseCode = "204", description = "Product updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class)))
    @PutMapping("/{id}")
    ResponseEntity<Mono<ProductResponse>> update(@PathVariable String id, @RequestBody ProductRequest productRequest,@RequestHeader(required = false) String db) {
            productRequest.setProductId(id);
            Mono<Product> result = productUseCase.updateProduct(id, db,productRestMapper.mapProductRequestToProduct(productRequest));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result.map(productRestMapper::mapProductToProductResponse));
    }

    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class)))
    @GetMapping()
    ResponseEntity<Flux<ProductResponse>> listAll(@RequestHeader(required = false) String db) {
        Flux<Product> result = productUseCase.listProduct(db);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result.map(productRestMapper::mapProductToProductResponse));
    }

    @ApiResponse(responseCode = "200", description = "Product retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class)))
    @GetMapping("/{id}")
    ResponseEntity<Mono<ProductResponse>> getById(@PathVariable String id,@RequestHeader(required = false) String db) {
        Mono<Product> result = productUseCase.getProductById(id,db);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result.map(productRestMapper::mapProductToProductResponse));
    }
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable String id,@RequestHeader(required = false) String db) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productUseCase.deleteProduct(id,db));
    }

}
