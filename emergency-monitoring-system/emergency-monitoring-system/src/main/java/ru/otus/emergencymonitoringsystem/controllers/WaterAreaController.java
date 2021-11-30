package ru.otus.emergencymonitoringsystem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.emergencymonitoringsystem.dto.DTOMapper;
import ru.otus.emergencymonitoringsystem.dto.WaterAreaDTO;
import ru.otus.emergencymonitoringsystem.repositories.WaterAreaRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@AllArgsConstructor
public class WaterAreaController {
    private final WaterAreaRepository waterAreaRepository;
    private final DTOMapper dtoMapper;

    @GetMapping("/api/waterAreas")
    public List<WaterAreaDTO> emergencies() {
        return waterAreaRepository.findAll().stream().map(x -> dtoMapper.waterAreaToDto(x)).collect(Collectors.toList());
    }
}
