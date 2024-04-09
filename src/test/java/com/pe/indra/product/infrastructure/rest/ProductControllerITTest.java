package com.pe.indra.product.infrastructure.rest;

import com.pe.indra.product.infrastructure.rest.model.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void create() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setDescription("Maquinaria Pesada");
        productRequest.setType("2");
        productRequest.setBrandId("1");
        productRequest.setTitle("Tractor");
        productRequest.setQuantity(2);
        productRequest.setSalePrice(new BigDecimal(1000));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "r2dbc");
        webTestClient.post()
                .uri("http://localhost:" + port + "/product/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isNotEmpty();
    }

    @Test
    void createError() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setDescription("Maquinaria Pesada");
        productRequest.setType("2");
        productRequest.setBrandId("1");
        productRequest.setTitle("Tractor");
        productRequest.setQuantity(2);
        productRequest.setSalePrice(new BigDecimal(1000));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "r2dbc2");
        webTestClient.post()
                .uri("http://localhost:" + port + "/product/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void update() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setDescription("Maquinaria Pesada");
        productRequest.setType("2");
        productRequest.setBrandId("1");
        productRequest.setTitle("Tractor");
        productRequest.setQuantity(2);
        productRequest.setSalePrice(new BigDecimal(1000));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "r2dbc");
        webTestClient.put()
                .uri("http://localhost:" + port + "/product/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isNotEmpty();
    }

    @Test
    void updateError() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setDescription("Maquinaria Pesada");
        productRequest.setType("2");
        productRequest.setBrandId("1");
        productRequest.setTitle("Tractor");
        productRequest.setQuantity(2);
        productRequest.setSalePrice(new BigDecimal(1000));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "mongo2");
        webTestClient.put()
                .uri("http://localhost:" + port + "/product/v1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(productRequest)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void getById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "r2dbc");
        webTestClient.get()
                .uri("http://localhost:" + port + "/product/v1/1")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.productId").isNotEmpty();
    }

    @Test
    void getByIdError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "mongo2");
        webTestClient.get()
                .uri("http://localhost:" + port + "/product/v1/2")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void listAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "r2dbc");
        webTestClient.get()
                .uri("http://localhost:" + port + "/product/v1")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }
    @Test
void listAllError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "mongo2");
        webTestClient.get()
                .uri("http://localhost:" + port + "/product/v1")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "r2dbc");
        webTestClient.delete()
                .uri("http://localhost:" + port + "/product/v1/2")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    void deleteError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("db", "mongo2");
        webTestClient.delete()
                .uri("http://localhost:" + port + "/product/v1/2")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .exchange()
                .expectStatus().is4xxClientError();
    }
}