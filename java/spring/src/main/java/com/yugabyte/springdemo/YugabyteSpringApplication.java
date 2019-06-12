package com.yugabyte.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YugabyteSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(YugabyteSpringApplication.class, args);
	}

}
