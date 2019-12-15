package com.marketplace;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.marketplace.chron.CartChrom;
import com.marketplace.configuration.SpringExtension;
import com.marketplace.v1.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;


@SpringBootApplication
public class Application {

	public static ActorRef cartChrom;

	public static void main(String[] args) {
		final ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		ActorSystem actorSystem = applicationContext.getBean(ActorSystem.class);

		cartChrom = actorSystem.actorOf(SpringExtension.SpringExtProvider.get(actorSystem).props("CartChrom"));
		cartChrom.tell(new CartChrom.Schedule(new Date()),null);

	}

	@Bean
	public CommandLineRunner addProduct(ProductService productService){
		return (args ->  productService.init());
	}

}
