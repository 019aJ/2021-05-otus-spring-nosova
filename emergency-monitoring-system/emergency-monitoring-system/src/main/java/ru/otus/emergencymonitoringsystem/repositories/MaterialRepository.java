package ru.otus.emergencymonitoringsystem.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.emergencymonitoringsystem.models.Material;

public interface MaterialRepository extends MongoRepository<Material, String> {
    Material findByIdentifier(String identifier);
}
