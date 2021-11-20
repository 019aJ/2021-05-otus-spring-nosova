package ru.otus.emergencymonitoringsystem.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.emergencymonitoringsystem.models.EmergencyType;

public interface EmergencyTypeRepository extends MongoRepository<EmergencyType, String> {
    EmergencyType findByIdentifier(String identifier);
}
