package br.com.financeirojavaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FinanceiroJavaSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroJavaSpringApplication.class, args);
	}

}
