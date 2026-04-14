package com.abdelrahman.shoppingcart;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ShoppingCartApiApplication {

	public static void main(String[] args) {
		
	//	SpringApplication.run(ShoppingCartApiApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(ShoppingCartApiApplication.class, args);
	    String[] activeProfiles = context.getEnvironment().getActiveProfiles();
	    System.out.println("#########################################");
	    System.out.println("Active Profiles: " + Arrays.toString(activeProfiles));
	    System.out.println("#########################################");
	}

}
