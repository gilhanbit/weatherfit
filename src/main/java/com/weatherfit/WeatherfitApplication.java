package com.weatherfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WeatherfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherfitApplication.class, args);
	}

}
