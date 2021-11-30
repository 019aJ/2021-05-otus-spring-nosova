package ru.otus.emergencymonitoringsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.geo.Polygon;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.otus.emergencymonitoringsystem.controllers.EmergencyMonitoringController;
import ru.otus.emergencymonitoringsystem.dto.CalculationResult;
import ru.otus.emergencymonitoringsystem.models.ContaminationArea;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;
import ru.otus.emergencymonitoringsystem.repositories.ContaminationAreaRepository;
import ru.otus.emergencymonitoringsystem.services.CalculationService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Тест контроллера с ЧС")
public class EmergencyMonitoringControllerTest {
    private final HttpHeaders headers = new HttpHeaders();
    @MockBean
    CalculationService mockCalculationService;
    @MockBean
    ContaminationAreaRepository contaminationAreaRepository;
    @Autowired
    private EmergencyMonitoringController сontroller;
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Finished monitorings")
    public void emergencyMonitorings() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth("admin", "admin");
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/emergency-monitorings"),
                HttpMethod.GET, entity, String.class);
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotEquals(response.getBody().length(), 0);
    }

    @Test
    @DisplayName("Active monitorings")
    public void activeEmergencyMonitorings() throws Exception {
        Mockito.when(mockCalculationService.getCalculationResult(any(EmergencyMonitoring.class), any(Polygon.class), any(Date.class))).thenReturn(new CalculationResult());
        Mockito.when(contaminationAreaRepository.save(any(ContaminationArea.class))).thenReturn(new ContaminationArea());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        TestRestTemplate restTemplate = new TestRestTemplate().withBasicAuth("admin", "admin");
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/active-emergency-monitorings"),
                HttpMethod.GET, entity, String.class);
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotEquals(response.getBody().length(), 0);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
