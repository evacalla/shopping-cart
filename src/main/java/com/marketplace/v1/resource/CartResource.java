package com.marketplace.v1.resource;

import com.marketplace.v1.service.CartService;
import com.marketplace.v1.vo.CartVO;
import com.marketplace.v1.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by evacalla on 5/12/2019
 **/

@RestController(CartResource.NAME)
@RequestMapping(value = CartResource.PATH)
public class CartResource {

    public static final String PATH = "/carts";
    public static final String NAME = "V1_CART";
    private CartService service;

    @Autowired
    public CartResource(CartService service) {
        this.service = service;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity createCart(@Valid @RequestBody CartVO vo){
        return ResponseEntity.ok(this.service.save(vo));
    }

    @RequestMapping(value = "/{id}/products", method = {RequestMethod.POST})
    public ResponseEntity addProduct(@PathVariable("id") Long id,@Valid @RequestBody ProductVO vo){
        this.service.addProduct(id, vo);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/products/{productId}", method = {RequestMethod.DELETE})
    public ResponseEntity removeProduct(@PathVariable("id") Long id, @PathVariable("productId") Long productId){
        this.service.removeProduct(id,productId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/products", method = {RequestMethod.GET})
    public ResponseEntity getProducts(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.service.findProductsById(id));
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public ResponseEntity getCart(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.service.convert2CartVO(id));
    }

    @RequestMapping(value = "/{id}/checkout", method = {RequestMethod.POST})
    public ResponseEntity changeStatusCart(@PathVariable("id") Long id){
        this.service.changeStatusCart(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/process", method = {RequestMethod.GET})
    public ResponseEntity changeStatusCart(){
        this.service.process();
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
