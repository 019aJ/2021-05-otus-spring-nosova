package ru.otus.libraryactuator.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

@Service
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        try {
            URL url = new URL("https://www.google.com/");
            int port = url.getPort();
            if (port == -1) {
                port = url.getDefaultPort();
            }

            try (Socket socket = new Socket(url.getHost(), port)) {
            } catch (IOException e) {
                return Health.down().withDetail("internet connection test", e.getMessage()).build();
            }
        } catch (MalformedURLException e1) {
            return Health.down().withDetail("internet connection test", e1.getMessage()).build();
        }
        return Health.up().build();
    }
}
