package ru.otus.emergencymonitoringsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;
import ru.otus.emergencymonitoringsystem.repositories.EmergencyMonitoringRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Тест ОРД")
class AclControllerTest {

    @Autowired
    private EmergencyMonitoringRepository сontroller;

    @Test
    @WithMockUser(
            username = "user",
            password = "user",
            authorities = {"ROLE_READER"}
    )
    @DisplayName("ЧС недоступны бесправному пользователю")
    public void testNoUserAllAuthors() throws Exception {
        List<EmergencyMonitoring> res = сontroller.activeWithLastPrediction();
        assertEquals(res.size(), 0);
    }

    @WithMockUser(
            username = "admin",
            password = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DisplayName("ЧС недоступны пользователю с ролью Админ")
    public void testAuthenticatedOnAdminAllAuthors() throws Exception {
        List<EmergencyMonitoring> res = сontroller.activeWithLastPrediction();
        assertNotEquals(res.size(), 0);
    }
}
