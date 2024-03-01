package com.swd391.backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class BackendApplication {

	@PostConstruct
	public void init() {
		//Connect to database
		System.out.println("Backend Application Started");
		//Bắt đầu kết nối với database
		DataSource dataSource = null;
		try {
			dataSource.getConnection();
			System.out.println("Database connected");
		} catch (Exception e) {
			System.out.println("Database connection failed");
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

