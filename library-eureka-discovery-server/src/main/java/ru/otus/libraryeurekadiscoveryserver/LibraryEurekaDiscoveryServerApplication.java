package ru.otus.libraryeurekadiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LibraryEurekaDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryEurekaDiscoveryServerApplication.class, args);
	}

}
