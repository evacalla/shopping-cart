package com.marketplace.v1.repository;


import com.marketplace.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartJpaRepository extends JpaRepository<Cart, Long>{


    @Query("SELECT c FROM Cart c WHERE c.status =?1")
    List<Cart> findByStatus(String status);

    Optional<Cart> findById(Long id);

}
