package ru.otus.coordtransform.controllers;

import lombok.AllArgsConstructor;
import org.osgeo.proj4j.*;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.coordtransform.models.CalculationInputData;
import ru.otus.coordtransform.models.CalculationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@AllArgsConstructor
public class TransformController {

    @PostMapping("/api/transformation")
    public CalculationResult calculation(@RequestBody CalculationInputData calculationInputData) {
        CalculationResult calculationResult = new CalculationResult();
        if (StringUtils.hasText(calculationInputData.getFrom())
                && StringUtils.hasText(calculationInputData.getTo())
                && calculationInputData.getGeometry() != null && !calculationInputData.getGeometry().isEmpty()) {
            CRSFactory factory = new CRSFactory();
            CoordinateReferenceSystem srcCrs = factory.createFromName(calculationInputData.getFrom());
            CoordinateReferenceSystem destCrs = factory.createFromName(calculationInputData.getTo());
            CoordinateTransform transform = new CoordinateTransformFactory().createTransform(srcCrs, destCrs);

            List<Polygon> positions = calculationInputData.getGeometry() != null ? calculationInputData.getGeometry().stream().filter(x -> x != null).map(polygon ->
                    new Polygon(polygon.getPoints().stream().map(point -> {
                        ProjCoordinate srcCoord = new ProjCoordinate(point.getX(), point.getY());
                        ProjCoordinate destCoord = new ProjCoordinate();
                        transform.transform(srcCoord, destCoord);
                        return new Point(destCoord.x, destCoord.y);
                    }).collect(Collectors.toList()))
            ).collect(Collectors.toList()) : new ArrayList<>();
            calculationResult.setGeometry(positions);
        }
        return calculationResult;
    }
}
