package com.marketplace.v1.service;

import com.marketplace.domain.Cart;
import com.marketplace.domain.Product;
import com.marketplace.domain.ProductCart;
import com.marketplace.exception.MarketPlaceNotFoundException;
import com.marketplace.v1.repository.CartJpaRepository;
import com.marketplace.v1.vo.CartVO;
import com.marketplace.v1.vo.ProductVO;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.OptimisticLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static sun.plugin2.message.StartAppletAckMessage.STATUS_READY;

/**
 * Created by evacalla on 5/12/2019
 **/

@Service
public class CartService {

    private CartJpaRepository cartJpaRepository;
    private ProductCartService productCartService;
    private ProductService productService;
    private static final String READY = "READY";
    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Autowired
    public CartService(CartJpaRepository cartJpaRepository,
                       ProductCartService productCartService,
                       ProductService productService) {
        this.cartJpaRepository = cartJpaRepository;
        this.productCartService = productCartService;
        this.productService = productService;
    }

    public Long save(CartVO vo) {
        LOGGER.info("CartService.save()");
        Cart cart = this.convert2Cart(vo);
        return this.cartJpaRepository.save(cart).getId();

    }

    public void addProduct(Long id, ProductVO vo){
        LOGGER.info("CartService.addProduct(" + id + ")");
        Cart cart = this.findCart(id);
        Product product = this.productService.findProduct(vo.getId());
        this.productCartService.save(cart, product, vo.getQuantity());
    }


    public void removeProduct(Long id, Long productId){
        LOGGER.info("CartService.removeProduct(" + id + ")");
        Cart cart = this.findCart(id);
        Product product = this.productService.findProduct(productId);
        this.productCartService.delete(cart, product);
    }

    public List<ProductVO> findProductsById(Long id){
        LOGGER.info("CartService.findProductsById(" + id + ")");
        Cart cart = this.findCart(id);
        return this.productCartService.findProductVOByCart(cart);
    }

    public void changeStatusCart(Long id){
        LOGGER.info("CartService.changeStatusCart(" + id + ")");
        Cart cart = this.findCart(id);
        cart.setStatus("READY");
        this.cartJpaRepository.save(cart);
    }

    public CartVO convert2CartVO(Long id){
        LOGGER.info("CartService.convert2CartVO(" + id + ")");
        Cart cart = this.findCart(id);
        CartVO vo = new CartVO();

        vo.setId(cart.getId());
        vo.setFullName(cart.getFullName());
        vo.setEmail(cart.getEmail());

        List<ProductVO> products = this.findProductsById(cart.getId());
        vo.setProducts(products.stream()
                .map(product -> product.getId())
                .collect(Collectors.toList()));

        vo.setTotal(products.stream()
                        .map(product -> product.getUnitPrice()
                                .multiply(new BigDecimal(product.getQuantity())))
                        .reduce(BigDecimal.ZERO, (a, b) -> b.add(a)));
        vo.setProducts(products.stream().map(ProductVO::getId).collect(Collectors.toList()));

        return vo;
    }

    public void process(){
        LOGGER.info("CartService.process()");
        List<Cart> carts = this.loadCartStatusReady();
        carts.forEach(cart -> this.runnable(cart));
    }

    @Transactional
    private void runnable(Cart cart) {
        LOGGER.info("CartService.runnable()");
        Runnable runnable = () -> {
            LOGGER.info("Cart prodcess id: " + cart.getId());
            if(this.thereAreStock(cart)) {
                List<ProductCart> productCarts = this.productCartService.getProductCartsByCartId(cart.getId());
                productCarts.forEach(productCart -> {
                    LOGGER.info("PROCESS cart id " + cart.getId() + "with productId " + productCart.getId().getProduct().getId() );
                    Integer rest = productCart.getId().getProduct().getStock() - productCart.getQuantity();
                    Product product = productCart.getId().getProduct();
                    product.setStock(rest);
                    this.productService.save(product);
                    LOGGER.info("PROCESS cart id: " + cart.getId());
                });
            } else {
                cart.setStatus("Faild cart id:" + cart.getId() );
                this.cartJpaRepository.save(cart);
            }
        };
        executorService.execute(runnable);
    }

    private Cart findCart(Long id) {
        return this.cartJpaRepository
                .findById(id)
                .orElseThrow(() -> new MarketPlaceNotFoundException("CartRunnable", "id", id));
    }

    private Cart convert2Cart(CartVO vo){
        Cart cart = new Cart();
        cart.setFullName(vo.getFullName());
        cart.setEmail(vo.getEmail());
        cart.setStatus("NEW");
        return cart;
    }

    private List<Cart> loadCartStatusReady(){
        return this.cartJpaRepository.findByStatus("READY");
    }

    private boolean thereAreStock(Cart cart) {
        List<ProductCart> productCarts = this.productCartService.getProductCartsByCartId(cart.getId());
        return productCarts.stream()
                .allMatch(product -> product.getId().getProduct().getStock() > product.getQuantity());
    }
}
