package com.rice.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RiceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiceBackendApplication.class, args);
		// This line launches the application.
		// It starts the embedded Tomcat web server (default port 8080).
		// 它会启动内嵌的 Tomcat 服务器 (默认端口 8080)
		// 它会初始化 Spring 容器，创建所有的 Controller, Repository 等对象
	}

}
