package ru.otus.emergencymonitoringsystem;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableMongock
@EnableConfigurationProperties
@EnableEurekaClient
@EnableCircuitBreaker
public class EmergencyMonitoringSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmergencyMonitoringSystemApplication.class, args);
    }
}
