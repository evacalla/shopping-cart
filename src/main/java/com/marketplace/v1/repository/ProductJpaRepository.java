package com.marketplace.v1.repository;


import com.marketplace.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);
}
