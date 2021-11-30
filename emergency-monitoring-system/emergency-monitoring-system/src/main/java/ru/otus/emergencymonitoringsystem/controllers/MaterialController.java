package ru.otus.emergencymonitoringsystem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.emergencymonitoringsystem.models.Material;
import ru.otus.emergencymonitoringsystem.repositories.MaterialRepository;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class MaterialController {
    private final MaterialRepository materialRepository;

    @GetMapping("/api/materials")
    public List<Material> materials() {
        return materialRepository.findAll();
    }
}
