package com.marketplace.v1.repository;

import com.marketplace.domain.ProductCart;
import com.marketplace.domain.ProductCart.ProductCartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCartJpaRepository extends JpaRepository<ProductCart, ProductCartId> {

    @Query("SELECT c FROM ProductCart c WHERE c.id.cart =?1")
    List<ProductCart> findByCrtCode(Long crtCode);


}
