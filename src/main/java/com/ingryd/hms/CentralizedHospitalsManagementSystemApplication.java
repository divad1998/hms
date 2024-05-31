package com.ingryd.hms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@SpringBootApplication
@EnableAsync
public class CentralizedHospitalsManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(CentralizedHospitalsManagementSystemApplication.class, args);
	}
}
