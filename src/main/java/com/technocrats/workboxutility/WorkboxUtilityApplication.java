package com.technocrats.workboxutility;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
public class WorkboxUtilityApplication {

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}

	public static void main(String[] args) {
		SpringApplication.run(WorkboxUtilityApplication.class, args);
	}
}
