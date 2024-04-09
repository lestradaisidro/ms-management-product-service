# Microservicio - MS-MANAGEMENT-PRODUCT-SERVICE

### Referencia de Documentación

Para mayor referencia, considere las siguientes secciones:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.4/maven-plugin/reference/html/#build-image)
* [Resilience4J](https://docs.spring.io/spring-cloud-circuitbreaker/docs/current/reference/html/#configuring-resilience4j-circuit-breakers)
* [Spring Data Reactive MongoDB](https://docs.spring.io/spring-boot/docs/3.2.4/reference/htmlsingle/index.html#data.nosql.mongodb)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.2.4/reference/htmlsingle/index.html#web.reactive)

## Requisitos Previos
- Java 17
- Maven 3.9.6
- Docker
- Docker Compose
- Lombok
- Spring Boot 3.2.4
- Resilience4J
- Spring Data Reactive MongoDB
- Drools
## Descripción

* Microservicio encargado de la gestión de productos.
* Microservicio reactivo con Spring WebFlux con arquitectura hexagonal.
* Se utiliza dos bases de datos, una en memoria H2 y otra en MongoDB según request enviada por el Header.

## Ejecutar en Desarrollo

### Paso 1: Clonar el repositorio

```sh
git clone -b develop https://github.com/lestradaisidro/ms-management-product-service.git
```
### Paso 2: Crear Contenedor Docker de Mongo que se encuentra en: `resources/docker-compose.yml`

```sh
docker-compose up -d
```
### Paso 3: Descargar e instalar Depedencias

```sh
mvn clean install
```
### Paso 4: Ejecutar Microservicio

```sh
mvn spring-boot:run
```
El microservicio levantará la aplicación en la url `http://localhost:8080/product`

## Probar la Aplicación

### Validar Swagger

[http://localhost:8080/product/v1/swagger-ui.html](http://localhost:8080/product/v1/swagger-ui.html)

### Validar Actuator

[http://localhost:8080/product/v1/actuator](http://localhost:8080/product/v1/actuator)

### Paso 5: Probar el servicio con Postman

Abrimos Postman e importamos la colección de peticiones que se encuentra en  `resources/postman/Product-WebFlux.postman_collection.json`

![image info](/src/main/resources/images/ayuda_postman.jpg)
### Paso 6: Testeo
```sh
mvn test
```
En la consola debe mostrar que todos los test fueron ejecutador correctamente

### Paso 7: Casos de Prueba

* Para probar en la bd r2dbc, se debe enviar el header `db: r2dbc`
* Para probar en la bd mongodb, se debe enviar el header `db: mongo`
* Para pruebas de resilencia y reintento ,detener contenedor de docker mongo y enviar peticiones
con el  header `db: mongo`

Esta decision la realiza drools, en el archivo `resources/rules/repositoryDecision.drl`

