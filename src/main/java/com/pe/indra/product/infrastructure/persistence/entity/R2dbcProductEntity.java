package com.pe.indra.product.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("products")
public class R2dbcProductEntity {
    @Id
    @Column("id")
    private Long id;
    @Column("TITLE")
    private String title;
    @Column("DESCRIPTION")
    private String description;
    @Column("BRAND_ID")
    private String brandId;
    @Column("SALE_PRICE")
    private BigDecimal salePrice;
    @Column("QUANTITY")
    private Integer quantity;
    @Column("DISCOUNT")
    private BigDecimal discount;
    @Column("TYPE")
    private String type;
}
