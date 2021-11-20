package ru.otus.coordtransform.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Polygon;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CalculationInputData {
    private List<Polygon> geometry;
    private String from;
    private String to;
}
