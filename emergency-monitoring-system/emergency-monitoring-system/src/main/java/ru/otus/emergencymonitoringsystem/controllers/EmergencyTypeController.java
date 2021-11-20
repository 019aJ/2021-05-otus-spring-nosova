package ru.otus.emergencymonitoringsystem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.emergencymonitoringsystem.models.EmergencyType;
import ru.otus.emergencymonitoringsystem.repositories.EmergencyTypeRepository;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class EmergencyTypeController {
    private final EmergencyTypeRepository emergencyTypeRepository;

    @GetMapping("/api/emergencyTypes")
    public List<EmergencyType> emergencyTypes() {
        return emergencyTypeRepository.findAll();
    }
}
