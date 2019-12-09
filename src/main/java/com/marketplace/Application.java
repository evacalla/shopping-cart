package com.marketplace;


import com.marketplace.domain.Product;
import com.marketplace.v1.repository.ProductJpaRepository;
import com.marketplace.v1.service.ProductService;
import org.hibernate.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner addProduct(ProductService productService){
		return (args ->  productService.init());
	}

}
