package ru.otus.emergencymonitoringsystem.dto;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.stereotype.Service;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;
import ru.otus.emergencymonitoringsystem.models.WaterArea;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOMapper {
    public EmergencyMonitoringDTO toDto(EmergencyMonitoring entity) {
        EmergencyMonitoringDTO dto = EmergencyMonitoringDTO.builder()
                .id(entity.getId())
                .createDate(entity.getCreateDate())
                .emergencyType(entity.getEmergencyType())
                .finishDate(entity.getFinishDate())
                .material(entity.getMaterial())
                .waterArea(waterAreaToDto(entity.getWaterArea()))
                .initialCoordinates(toWkt(entity.getInitialCoordinates()))
                .predictionCoordinates(entity.getContaminationArea() != null && entity.getContaminationArea().getCoordinates() != null ? toWktList(entity.getContaminationArea().getCoordinates()) : null)
                .build();
        return dto;
    }

    public EmergencyMonitoring toEntity(EmergencyMonitoringDTO dto) {
        EmergencyMonitoring entity = EmergencyMonitoring.builder()
                .id(dto.getId())
                .createDate(dto.getCreateDate())
                .finishDate(dto.getFinishDate())
                .material(dto.getMaterial())
                .waterArea(waterAreaToEntity(dto.getWaterArea()))
                .emergencyType(dto.getEmergencyType())
                .build();
        entity.setInitialCoordinates(fromWkt(dto.getInitialCoordinates()));
        return entity;
    }

    public WaterAreaDTO waterAreaToDto(WaterArea entity) {
        WaterAreaDTO dto = WaterAreaDTO.builder()
                .id(entity.getId())
                .identifier(entity.getIdentifier())
                .name(entity.getName())
                .build();
        dto.setCoordinates(entity.getCoordinates() != null ? toWkt(entity.getCoordinates()) : null);
        return dto;
    }

    public WaterArea waterAreaToEntity(WaterAreaDTO dto) {
        WaterArea entity = WaterArea.builder()
                .id(dto.getId())
                .identifier(dto.getIdentifier())
                .name(dto.getName())
                .build();
        entity.setCoordinates(dto.getCoordinates() != null ? fromWkt(dto.getCoordinates()) : null);
        return entity;
    }

    public List<EmergencyMonitoringDTO> toDto(List<EmergencyMonitoring> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Polygon fromWkt(String wkt) {
        if (Strings.isNullOrEmpty(wkt)) {
            return null;
        }
        String normalizeSpace = StringUtils.normalizeSpace(wkt.trim());
        String[] pointsStr = normalizeSpace.substring(9, normalizeSpace.length() - 2).split(",");
        List<Point> points =
                Arrays.stream(pointsStr).map(p -> {
                    String[] coord = p.trim().split(" ");

                    return new Point(Double.parseDouble(coord[0].trim()), Double.parseDouble(coord[1].trim()));
                }).collect(Collectors.toList());
        return new Polygon(points);
    }

    private String toWktList(List<Polygon> polygon) {
        return String.join(";", polygon.stream().map(this::toWkt).collect(Collectors.toList()));
    }

    private String toWkt(Polygon polygon) {
        if (polygon == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("POLYGON((");
        sb.append(String.join(",", polygon.getPoints().stream().map(p -> p.getX() + " " + p.getY()).collect(Collectors.toList())));
        sb.append("))");
        return sb.toString();
    }

    private List<Polygon> fromWktList(String wkt) {
        String[] pointsStr = wkt.split(";");
        return Arrays.stream(pointsStr).map(this::fromWkt).collect(Collectors.toList());
    }
}
