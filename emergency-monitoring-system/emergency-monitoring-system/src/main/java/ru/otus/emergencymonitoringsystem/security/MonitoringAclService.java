package ru.otus.emergencymonitoringsystem.security;

import ru.otus.emergencymonitoringsystem.models.EMSUser;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;
import ru.otus.emergencymonitoringsystem.models.WaterArea;

import java.util.List;

public interface MonitoringAclService {
    void addPermissionsForArea(WaterArea area, List<EMSUser> users);

    void addPermissionsForMonitoring(EmergencyMonitoring emergencyMonitoring);

    void removePermissionsOnDelete(Long id);
}
