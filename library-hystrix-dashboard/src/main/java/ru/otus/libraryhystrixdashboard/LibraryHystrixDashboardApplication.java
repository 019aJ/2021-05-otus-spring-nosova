package ru.otus.libraryhystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class LibraryHystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryHystrixDashboardApplication.class, args);
	}

}
