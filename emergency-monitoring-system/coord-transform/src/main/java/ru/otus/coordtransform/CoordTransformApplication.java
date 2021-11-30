package ru.otus.coordtransform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CoordTransformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoordTransformApplication.class, args);
	}

}
