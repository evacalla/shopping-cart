package com.marketplace.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by evacalla on 5/12/2019
 **/

@Entity
@Table(name = "PRODUCT_CART")
public class ProductCart {

    @Embeddable
    public static class ProductCartId implements Serializable {

        @OneToOne
        @JoinColumn(name = "PRO_CODE", referencedColumnName = "PRO_CODE")
        private Product product;

        @Column(name = "CRT_CODE")
        private Long cart;

        public ProductCartId() {
        }

        public ProductCartId(Product product, Long cart) {
            this.product = product;
            this.cart = cart;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductCartId that = (ProductCartId) o;
            return Objects.equals(product, that.product) &&
                    Objects.equals(cart, that.cart);
        }

        @Override
        public int hashCode() {
            return Objects.hash(product, cart);
        }

        public Product getProduct() { return product; }

        public void setProduct(Product product) { this.product = product; }

        public Long getCart() { return cart; }

        public void setCart(Long cart) { this.cart = cart; }
    }

    @Column(name = "PRO_CTR_QTY")
    private Integer quantity;

    @Column(name = "PRO_CTR_UNIT_PRICE")
    private BigDecimal price;

    @EmbeddedId
    private ProductCartId id;

    public ProductCartId getId() {
        return id;
    }

    public void setId(ProductCartId id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        ProductCart that = (ProductCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
