package com.neo.neomarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NeoMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeoMarketApplication.class, args);
	}

}
