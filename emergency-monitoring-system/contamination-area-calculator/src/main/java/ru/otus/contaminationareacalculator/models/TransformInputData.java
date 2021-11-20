package ru.otus.contaminationareacalculator.models;

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
public class TransformInputData {
    private List<Polygon> geometry;
    private String from;
    private String to;
}
