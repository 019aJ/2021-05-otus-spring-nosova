package ru.otus.emergencymonitoringsystemeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EmergencyMonitoringSystemEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmergencyMonitoringSystemEurekaApplication.class, args);
	}

}
