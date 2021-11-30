package ru.otus.emshystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class EmsHystrixDashboardApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmsHystrixDashboardApplication.class, args);
	}
}
