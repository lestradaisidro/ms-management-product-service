package com.pe.indra.product.infrastructure.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String title;
    private String description;
    private String brandId;
    private String type;
    private BigDecimal salePrice;
    private Integer quantity;
    private String productId;
    private BigDecimal discount;
}
