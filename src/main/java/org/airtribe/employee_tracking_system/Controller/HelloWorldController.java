package org.airtribe.employee_tracking_system.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello World!";
	}

	@GetMapping("/")
	public String home() {
		return "Welcome \nHome Sweet Home!";
	}

	@GetMapping("/api/hello")
	public String apiHello() {
		return "API Hello World";
	}
}
