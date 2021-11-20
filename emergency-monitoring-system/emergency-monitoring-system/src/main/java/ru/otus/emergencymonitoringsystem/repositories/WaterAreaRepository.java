package ru.otus.emergencymonitoringsystem.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.emergencymonitoringsystem.models.WaterArea;

public interface WaterAreaRepository extends MongoRepository<WaterArea, String> {
    WaterArea findByIdentifier(String identifier);
}
