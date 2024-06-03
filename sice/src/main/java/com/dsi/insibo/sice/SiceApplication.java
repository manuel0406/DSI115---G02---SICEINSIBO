package com.dsi.insibo.sice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@SpringBootApplication
public class SiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiceApplication.class, args);
	}

	@GetMapping("/home")
	public String holamundo() {
		return "home";
	}
	
}
