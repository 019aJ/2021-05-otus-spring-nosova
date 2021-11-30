package ru.otus.emergencymonitoringsystem.repositories;

import org.springframework.security.access.prepost.PostFilter;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;

import java.util.List;

public interface EmergencyMonitoringRepositoryCustom {
    EmergencyMonitoring findByIdWithLastPrediction(Long id);

    @PostFilter("hasRole('ADMIN') || (hasPermission(filterObject.waterArea, 'READ'))")
    List<EmergencyMonitoring> activeWithLastPrediction();
}
