package com.pe.indra.product.infrastructure.persistence.configuration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class PersistenceR2dbcConfig {

    @Value("${spring.r2dbc.url}")
    private String url;
    @Bean
    @ConfigurationProperties(prefix="spring.r2dbc")
    public ConnectionFactory connectionFactory() {
        return ConnectionFactoryBuilder.withUrl(url).build();
    }
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        ResourceDatabasePopulator resource =
                new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));

        initializer.setDatabasePopulator(resource);
        return initializer;
    }

}
