package com.marketplace.v1.service;

import com.marketplace.domain.Cart;
import com.marketplace.domain.Product;
import com.marketplace.domain.ProductCart;
import com.marketplace.domain.ProductCart.ProductCartId;
import com.marketplace.exception.MarketPlaceNotFoundException;
import com.marketplace.v1.repository.ProductCartJpaRepository;
import com.marketplace.v1.vo.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by evacalla on 5/12/2019
 **/

@Service
public class ProductCartService {

    private ProductCartJpaRepository productCartJpaRepository;
    private ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCartService.class);

    @Autowired
    public ProductCartService(ProductCartJpaRepository productCartJpaRepository,
                              ProductService productService) {
        this.productService = productService;
        this.productCartJpaRepository = productCartJpaRepository;
    }


    public void save(Cart cart, Product product, Integer quantity){
        LOGGER.info("ProductCartService.save()");
        ProductCart productCart = this.build(cart, product, quantity);
        this.productCartJpaRepository.save(productCart);

    }

    public void delete(Cart cart, Product product){
        LOGGER.info("ProductCartService.delete(" + cart.getId() + "," + product.getId() + ")");
        ProductCart productCart = this.getProductCart(cart, product);
        this.productCartJpaRepository.delete(productCart);
    }

    public List<ProductCart> getProductCartsByCartId(Long id){
        LOGGER.info("ProductCartService.getProductCarts(" + id + ")");
        return this.productCartJpaRepository.findByCrtCode(id);

    }

    public List<ProductVO> findProductVOByCart(Cart cart){
        List<ProductCart> productCarts = this.productCartJpaRepository.findByCrtCode(cart.getId());
        return productCarts.stream()
                .map(productCart -> this.productService.convet2VO(productCart))
                .collect(Collectors.toList());
    }

    private ProductCart getProductCart(Cart cart, Product product) {
        LOGGER.info("ProductCartService.getProductCart( " + cart.getId() + "," + product.getId() + ")");
        ProductCartId productCartId = new ProductCartId(product, cart.getId());
        return this.productCartJpaRepository.findById(productCartId)
                .orElseThrow(() -> new MarketPlaceNotFoundException("ProductCart", "id", productCartId));
    }

    private ProductCart build(Cart cart, Product product, Integer quantity) {
        ProductCart prodCart = new ProductCart();
        ProductCartId productCartId = new ProductCartId(product, cart.getId());
        prodCart.setId(productCartId);
        prodCart.setQuantity(quantity);
        prodCart.setPrice(product.getPrice());
        return prodCart;
    }
}
