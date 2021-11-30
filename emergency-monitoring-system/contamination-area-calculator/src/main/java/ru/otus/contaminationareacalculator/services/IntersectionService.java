package ru.otus.contaminationareacalculator.services;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IntersectionService {

    public Optional<Polygon> checkIntersection(Polygon sea, Polygon area) {
        if (sea != null) {
            org.locationtech.jts.geom.Polygon seaRegion = convert(sea);
            org.locationtech.jts.geom.Polygon areaRegion = convert(area);
            if (seaRegion.contains(areaRegion)) {
                return Optional.ofNullable(area);
            }
            return Optional.empty();
        }
        return Optional.ofNullable(area);
    }

    private org.locationtech.jts.geom.Polygon convert(Polygon polygon) {
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Coordinate> regions = polygon.getPoints().stream().map(p -> new Coordinate(p.getX(), p.getY())).collect(Collectors.toList());
        return geometryFactory.createPolygon(regions.toArray(new Coordinate[regions.size()]));
    }

    private Polygon convert(org.locationtech.jts.geom.Polygon polygon) {
        if (polygon.getCoordinates() != null && polygon.getCoordinates().length > 0) {
            return new Polygon(Arrays.asList(polygon.getCoordinates()).stream().map(p -> new Point(p.getX(), p.getY())).collect(Collectors.toList()));
        }
        return null;
    }
}
