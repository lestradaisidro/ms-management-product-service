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
public class ProductResponse {
    private String productId;
    private String title;
    private String description;
    private String brandId;
    private BigDecimal salePrice;
    private Integer quantity;
    private BigDecimal discount;
    private String type;
}
