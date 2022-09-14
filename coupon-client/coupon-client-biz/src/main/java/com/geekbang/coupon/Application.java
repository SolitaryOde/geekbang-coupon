package com.geekbang.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.geekbang.coupon")
@EntityScan(basePackages = "com.geekbang.coupon")
@EnableJpaAuditing
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
