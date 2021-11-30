package ru.otus.emergencymonitoringsystem.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;

import java.util.List;

public interface EmergencyMonitoringRepository extends MongoRepository<EmergencyMonitoring, String>, EmergencyMonitoringRepositoryCustom {
    List<EmergencyMonitoring> findByIsActive(Boolean isActive);
}
