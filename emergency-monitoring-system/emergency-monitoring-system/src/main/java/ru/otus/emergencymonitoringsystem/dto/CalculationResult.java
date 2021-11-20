package ru.otus.emergencymonitoringsystem.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Polygon;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = GeoJsonPolygonDeserializer.class)
public class CalculationResult {
    private List<Polygon> positions;
    private Integer period;
}
