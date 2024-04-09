package com.pe.indra.product.infrastructure.persistence.configuration;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.pe.indra.product.infrastructure.persistence")
public class PersistenceMongoConfig extends AbstractReactiveMongoConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
    @Bean
    public MongoClient reactiveMongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUri);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToClusterSettings(builder ->
                        builder.serverSelectionTimeout(2000, TimeUnit.MILLISECONDS)) // Establecer el tiempo de espera de conexión
                .build();

        return MongoClients.create(settings);
    }

}