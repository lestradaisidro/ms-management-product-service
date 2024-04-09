package com.pe.indra.product.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class MongoProductEntity {
    @Id
    private String id;
    private String title;
    private String description;
    private String brandId;
    private BigDecimal salePrice;
    private Integer quantity;
    private BigDecimal discount;
    private String type;
}
