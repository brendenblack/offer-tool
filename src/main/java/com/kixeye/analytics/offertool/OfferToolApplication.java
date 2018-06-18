package com.kixeye.analytics.offertool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.kixeye.analytics.offertool.domain.models")
@EnableJpaRepositories
public class OfferToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfferToolApplication.class, args);
	}
}
