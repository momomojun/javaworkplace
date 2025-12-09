package com.rice.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RiceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiceBackendApplication.class, args);
		// This line launches the application.
		// It starts the embedded Tomcat web server (default port 8080).
	}

}
