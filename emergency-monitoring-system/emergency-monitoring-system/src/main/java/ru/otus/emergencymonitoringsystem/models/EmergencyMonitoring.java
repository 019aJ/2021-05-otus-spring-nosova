package ru.otus.emergencymonitoringsystem.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "EmergencyMonitoring")
public class EmergencyMonitoring {
    @Id
    private String id;

    @Field("create_date")
    private Date createDate;

    @Field("finish_date")
    private Date finishDate;

    @Field("initial_coordinates")
    private Polygon initialCoordinates;

    @Field("is_active")
    private Boolean isActive;

    @Field("contamination_area")
    private ContaminationArea contaminationArea;

    @Field("waterArea")
    private WaterArea waterArea;

    @Field("material")
    private Material material;

    @Field("emergency_type")
    private EmergencyType emergencyType;
}
