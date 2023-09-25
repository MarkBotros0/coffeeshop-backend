package com.coffeeShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.coffeeShop.service.ProductService;

@SpringBootApplication
public class CoffeeShopApplication {

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(CoffeeShopApplication.class, args);

		ProductService ps = appContext.getBean("productService", ProductService.class);
		ps.init();
//		OrderService os = appContext.getBean("orderService", OrderService.class);
//		os.init();

	}

}
