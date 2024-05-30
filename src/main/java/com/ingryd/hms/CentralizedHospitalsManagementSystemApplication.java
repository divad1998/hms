package com.ingryd.hms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CentralizedHospitalsManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralizedHospitalsManagementSystemApplication.class, args);
	}

}
