package ru.otus.contaminationareacalculator.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Polygon;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalculationInputData {
    private Polygon currentPosition;
    private Double density;
    private Polygon waterAreaLocation;
    private Date positionTime;
}
