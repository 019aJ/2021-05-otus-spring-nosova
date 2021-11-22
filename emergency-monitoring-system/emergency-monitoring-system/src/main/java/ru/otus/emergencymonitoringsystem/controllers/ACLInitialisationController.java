package ru.otus.emergencymonitoringsystem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.emergencymonitoringsystem.models.EMSUser;
import ru.otus.emergencymonitoringsystem.repositories.EMSUserRepository;
import ru.otus.emergencymonitoringsystem.repositories.EmergencyMonitoringRepository;
import ru.otus.emergencymonitoringsystem.repositories.WaterAreaRepository;
import ru.otus.emergencymonitoringsystem.security.MonitoringAclService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
/**Создает для существующих EmergencyMonitoring записи в ACL(актуально только для записей, создающихся через InitMongoDBDataChangeLog, при работе с апи не требуется) */
public class ACLInitialisationController {
    private final WaterAreaRepository waterAreaRepository;
    private final EMSUserRepository emsUserRepository;
    private final MonitoringAclService monitoringAclService;
    private final EmergencyMonitoringRepository emergencyMonitoringRepository;

    @GetMapping("/api/acl-initialization")
    public void initAcl() {
        List<EMSUser> users = emsUserRepository.findAll();
        Map<String, EMSUser> map = new HashMap<>();
        users.forEach(user -> {
            switch (user.getName()) {
                case "user_km":
                    map.put("КАСПИЙСКОЕ МОРЕ", user);
                    break;
                case "user_bm":
                    map.put("БАЛТИЙСКОЕ МОРЕ", user);
                    break;
                case "user_ss":
                    map.put("САХАЛИНСКИЙ ШЕЛЬФ", user);
                    break;
                case "user_pm":
                    map.put("ПЕЧОРСКОЕ МОРЕ", user);
                    break;
            }
        });
        waterAreaRepository.findAll().forEach(e ->
                monitoringAclService.addPermissionsForArea(e, Arrays.asList(map.get(e.getIdentifier())))
        );
        emergencyMonitoringRepository.findAll().forEach(monitoringAclService::addPermissionsForMonitoring);
    }
}
