package com.example.rdcmmatricula2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsMatricula2Application {

	public static void main(String[] args) {
		SpringApplication.run(MsMatricula2Application.class, args);
	}

}
