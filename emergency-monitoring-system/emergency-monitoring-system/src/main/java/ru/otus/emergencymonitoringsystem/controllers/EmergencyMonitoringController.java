package ru.otus.emergencymonitoringsystem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.emergencymonitoringsystem.dto.CalculationResult;
import ru.otus.emergencymonitoringsystem.dto.DTOMapper;
import ru.otus.emergencymonitoringsystem.dto.EmergencyMonitoringDTO;
import ru.otus.emergencymonitoringsystem.models.*;
import ru.otus.emergencymonitoringsystem.repositories.*;
import ru.otus.emergencymonitoringsystem.security.MonitoringAclService;
import ru.otus.emergencymonitoringsystem.services.CalculationService;
import ru.otus.emergencymonitoringsystem.services.PredictionService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class EmergencyMonitoringController {
    public static final int DEFAULT_PERIOD = 60;
    private final MaterialRepository materialRepository;
    private final WaterAreaRepository waterAreaRepository;
    private final EmergencyTypeRepository emergencyTypeRepository;
    private final EmergencyMonitoringRepository emergencyMonitoringRepository;
    private final ContaminationAreaRepository contaminationAreaRepository;

    private final DTOMapper emergencyMonitoringMapper;

    private final MonitoringAclService monitoringAclService;
    private final CalculationService calculationService;
    private final PredictionService predictionService;

    @GetMapping("/api/emergency-monitorings/{emergencyMonitoringId}")
    public ResponseEntity<EmergencyMonitoring> emergencyMonitoring(@PathVariable String emergencyMonitoringId) throws URISyntaxException {
        Optional<EmergencyMonitoring> emergencyMonitoring = emergencyMonitoringRepository.findById(emergencyMonitoringId);
        return emergencyMonitoring.map(em -> ResponseEntity.ok().body(em))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/emergency-monitorings")
    public List<EmergencyMonitoringDTO> emergencyMonitorings() {
        List<EmergencyMonitoring> emergencyMonitorings = emergencyMonitoringRepository.findByIsActive(false);
        return emergencyMonitoringMapper.toDto(emergencyMonitorings);
    }

    @GetMapping("/api/active-emergency-monitorings")
    public List<EmergencyMonitoringDTO> activeEmergencyMonitorings() {
        List<EmergencyMonitoring> emergencyMonitoring = emergencyMonitoringRepository.activeWithLastPrediction();
        //Т.к. у нас игрушечное приложение, пересчитывать прогнозы будем при запросе данных.
        //Вообще они должны считаться по какому-то таймеру
        recalculatePredictions(emergencyMonitoring);
        return emergencyMonitoringMapper.toDto(emergencyMonitoring);
    }

    @PostMapping("/api/emergency-monitorings")
    public ResponseEntity<EmergencyMonitoring> createEmergencyMonitoring(@RequestBody EmergencyMonitoringDTO input) throws URISyntaxException {
        EmergencyMonitoring inputEmergencyMonitoring = emergencyMonitoringMapper.toEntity(input);
        //создается мониторинг ЧС
        EmergencyMonitoring emergencyMonitoring = createEmergencyMonitoring(inputEmergencyMonitoring);
        //создается первый прогноз
        CalculationResult positions = calculationService.getCalculationResult(emergencyMonitoring, emergencyMonitoring.getInitialCoordinates(), emergencyMonitoring.getCreateDate());
        //и сохраняется информация о загрязненной площади
        ContaminationArea contaminationArea = createContaminationArea(emergencyMonitoring, positions);
        emergencyMonitoring.setContaminationArea(contaminationArea);
        //ОРД
        monitoringAclService.addPermissionsForMonitoring(emergencyMonitoring);
        return ResponseEntity.created(new URI("/api/emergency-monitorings/" + emergencyMonitoring.getId()))
                .body(emergencyMonitoring);
    }

    @PutMapping("/api/emergency-monitorings")
    public ResponseEntity<EmergencyMonitoringDTO> finishEmergencyMonitoring(@RequestBody EmergencyMonitoringDTO emergencyMonitoringDTO) {
        EmergencyMonitoring emergencyMonitoring = emergencyMonitoringMapper.toEntity(emergencyMonitoringDTO);
        emergencyMonitoring.setIsActive(false);
        emergencyMonitoring.setFinishDate(new Date());
        EmergencyMonitoring result = emergencyMonitoringRepository.save(emergencyMonitoring);
        return ResponseEntity.ok().body(emergencyMonitoringMapper.toDto(emergencyMonitoring));
    }

    private void recalculatePredictions(List<EmergencyMonitoring> emergencyMonitorings) {
        emergencyMonitorings.forEach(monitoring -> {
                    try {
                        CalculationResult positions = predictionService.recalculatePredictions(monitoring);
                        ContaminationArea contaminationArea = createContaminationArea(monitoring, positions);
                        monitoring.setContaminationArea(contaminationArea);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private ContaminationArea createContaminationArea(EmergencyMonitoring emergencyMonitoring, CalculationResult positions) {
        ContaminationArea contaminationArea = new ContaminationArea();
        contaminationArea.setDataType(new DataType("Прогноз"));
        contaminationArea.setEmergencyMonitoringId(emergencyMonitoring.getId());
        contaminationArea.setCreateDate(new Date());
        contaminationArea.setCoordinates(positions.getPositions());
        contaminationArea.setPeriod(positions.getPeriod() != null ? positions.getPeriod() : DEFAULT_PERIOD);
        contaminationArea = contaminationAreaRepository.save(contaminationArea);
        return contaminationArea;
    }

    private EmergencyMonitoring createEmergencyMonitoring(EmergencyMonitoring inputEmergencyMonitoring) {
        WaterArea waterArea = waterAreaRepository.findByIdentifier(inputEmergencyMonitoring.getWaterArea().getIdentifier());
        Material material = materialRepository.findByIdentifier(inputEmergencyMonitoring.getMaterial().getIdentifier());
        EmergencyType emergencyType = emergencyTypeRepository.findByIdentifier(inputEmergencyMonitoring.getEmergencyType().getIdentifier());
        EmergencyMonitoring emergencyMonitoring = new EmergencyMonitoring();
        emergencyMonitoring.setEmergencyType(emergencyType);
        emergencyMonitoring.setMaterial(material);
        emergencyMonitoring.setWaterArea(waterArea);
        emergencyMonitoring.setCreateDate(new Date());
        emergencyMonitoring.setInitialCoordinates(inputEmergencyMonitoring.getInitialCoordinates());
        emergencyMonitoring.setIsActive(!Boolean.FALSE.equals(inputEmergencyMonitoring.getIsActive()));
        emergencyMonitoring = emergencyMonitoringRepository.save(emergencyMonitoring);
        return emergencyMonitoring;
    }
}
