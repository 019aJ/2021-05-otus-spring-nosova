package ru.otus.emergencymonitoringsystem.dto;

import lombok.*;
import ru.otus.emergencymonitoringsystem.models.EmergencyType;
import ru.otus.emergencymonitoringsystem.models.Material;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyMonitoringDTO {
    private String id;

    private Date createDate;
    private Date finishDate;
    private String initialCoordinates;

    private WaterAreaDTO waterArea;
    private Material material;
    private EmergencyType emergencyType;

    private String predictionCoordinates;
}
