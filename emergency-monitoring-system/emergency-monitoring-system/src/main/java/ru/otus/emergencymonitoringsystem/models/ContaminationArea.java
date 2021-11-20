package ru.otus.emergencymonitoringsystem.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "ContaminationAreas")
public class ContaminationArea {
    @Id
    private String id;

    @Field("create_date")
    private Date createDate;

    @Field("coordinates")
    private List<Polygon> coordinates;

    @Field("emergency_monitoring_id")
    private String emergencyMonitoringId;

    @Field("data_type")
    private DataType dataType;

    @Field("period")
    private Integer period;
}
