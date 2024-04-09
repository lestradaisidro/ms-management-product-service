package com.pe.indra.product.application;

import com.pe.indra.product.domain.entity.Product;
import com.pe.indra.product.domain.repository.ProductRepository;
import com.pe.indra.product.domain.usercases.ProductUseCase;
import com.pe.indra.product.exception.BusinessException;
import com.pe.indra.product.infrastructure.persistence.MongoProductRepositoryImpl;
import com.pe.indra.product.infrastructure.persistence.R2dbcProductRepositoryImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.pe.indra.product.infrastructure.persistence.util.Constants.ERROR_REPOSITORIO;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductUserCaseImpl implements ProductUseCase {

    @Setter
    private ProductRepository productRepository;

    @Getter
    @Setter
    private String db;
    private final KieContainer kieContainer;
    private final MongoProductRepositoryImpl mongoProductRepository;
    private final R2dbcProductRepositoryImpl r2dbcProductRepository;


    @Override
    public Flux<Product> listProduct(String db) {
        return getRepository(db).listProduct();

    }

    @Override
    public Mono<Product> getProductById(String id, String db) {
        return getRepository(db).getProductById(id);
    }

    @Override
    public Mono<Product> createProduct(Product product, String db) {
        return getRepository(db).createProduct(product);
    }

    @Override
    public Mono<Product> updateProduct(String id, String db, Product product) {
        return getRepository(db).updateProduct(id, product);
    }

    @Override
    public Mono<Void> deleteProduct(String id, String db) {
        return getRepository(db).deleteProduct(id);
    }

    private ProductRepository getRepository(String db) {
        productRepository=null;
        KieSession kieSession = kieContainer.newKieSession("rulesSession");
        try {
            kieSession.setGlobal("mongoProductRepository", mongoProductRepository);
            kieSession.setGlobal("r2dbcProductRepository", r2dbcProductRepository);
            this.setDb(db);
            kieSession.insert(this);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        if (productRepository == null) {
            throw new BusinessException(ERROR_REPOSITORIO + db,HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return productRepository;
    }
}
