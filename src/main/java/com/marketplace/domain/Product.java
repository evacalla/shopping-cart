package com.marketplace.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by evacalla on 5/12/2019
 **/

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PRO_CODE", unique = true, nullable = false)
    private Long id;

    @Column(name = "PRO_DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PRO_STOCK", nullable = false)
    private Integer stock;

    @Column(name = "PRO_UNIT_PRICE", nullable = false)
    private BigDecimal price;

    public Product() { }

    public Product(String description, Integer stock, BigDecimal price) {
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
