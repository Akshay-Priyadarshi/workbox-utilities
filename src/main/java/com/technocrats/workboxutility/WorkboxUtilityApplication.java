package com.technocrats.workboxutility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WorkboxUtilityApplication {


	@GetMapping("/hello/{name}")
	public String sayHello(@PathVariable("name") String name) {
		return String.format("Hello %s!", name);
	}

	public static void main(String[] args) {
		SpringApplication.run(WorkboxUtilityApplication.class, args);
	}
}
