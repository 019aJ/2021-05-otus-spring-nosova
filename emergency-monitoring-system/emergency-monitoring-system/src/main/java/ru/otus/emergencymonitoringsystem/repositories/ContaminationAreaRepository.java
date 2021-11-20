package ru.otus.emergencymonitoringsystem.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.emergencymonitoringsystem.models.ContaminationArea;

public interface ContaminationAreaRepository extends MongoRepository<ContaminationArea, String> {
}
