package ru.otus.emergencymonitoringsystem.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.emergencymonitoringsystem.models.EMSUser;

public interface EMSUserRepository extends MongoRepository<EMSUser, String> {
    EMSUser findByName(String name);
}
