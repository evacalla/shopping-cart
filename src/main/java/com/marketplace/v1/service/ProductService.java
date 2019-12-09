package com.marketplace.v1.service;

import com.marketplace.domain.Product;
import com.marketplace.domain.ProductCart;
import com.marketplace.exception.MarketPlaceNotFoundException;
import com.marketplace.v1.repository.ProductJpaRepository;
import com.marketplace.v1.vo.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evacalla on 5/12/2019
 **/
@Service
public class ProductService {

    private ProductJpaRepository productJpaRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public void init(){
        List<Product> products = new ArrayList<>();
        products.add(new Product("Palta 2gk", 30, new BigDecimal(235.99)));
        products.add(new Product("Tomate 2gk", 60, new BigDecimal(100.20)));
        products.add(new Product("Limon 2gk", 20, new BigDecimal(499.99)));
        products.add(new Product("Manzana 2gk", 10, new BigDecimal(200.00)));
        products.forEach(product -> this.productJpaRepository.save(product));
    }

    public Product findProduct(Long id){
        LOGGER.info("ProductService.findProduct(" + id + ")");
        return this.productJpaRepository.findById(id)
                .orElseThrow(() -> new MarketPlaceNotFoundException("Product", "id", id));
    }

    public ProductVO convet2VO(ProductCart productCart){
        ProductVO vo = new ProductVO();
        vo.setId(productCart.getId().getProduct().getId());
        vo.setDescription(productCart.getId().getProduct().getDescription());
        vo.setQuantity(productCart.getQuantity());
        vo.setUnitPrice(productCart.getPrice());
        return vo;
    }

    /*
    List<ProductCart> productCarts = this.productCartService.getProductCartsByCartId(cart.getId());
        List<ProductVO> vos = new ArrayList<>();
        productCarts.forEach(productCart -> {
            ProductVO vo = new ProductVO();
            vo.setId(productCart.getId().getProduct().getId());
            vo.setDescription(productCart.getId().getProduct().getDescription());
            vo.setQuantity(productCart.getQuantity());
            vo.setUnitPrice(productCart.getPrice());
            vos.add(vo);
        });
        return vos;
     */


    public void save(Product product){
        this.productJpaRepository.save(product);
    }


}
