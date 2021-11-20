package ru.otus.weatherforecast.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Polygon;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CalculationInputData {
    private Double lon;
    private Double lat;
}
