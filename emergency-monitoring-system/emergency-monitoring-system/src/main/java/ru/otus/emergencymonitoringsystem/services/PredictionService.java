package ru.otus.emergencymonitoringsystem.services;

import lombok.AllArgsConstructor;
import org.springframework.data.geo.Polygon;
import org.springframework.stereotype.Service;
import ru.otus.emergencymonitoringsystem.dto.CalculationResult;
import ru.otus.emergencymonitoringsystem.models.ContaminationArea;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PredictionService {
    private final CalculationService calculationService;

    public CalculationResult recalculatePredictions(EmergencyMonitoring monitoring) throws URISyntaxException {
        Polygon currentPolygon = monitoring.getInitialCoordinates();
        Date polygonDate = new Date();
        List<Polygon> lastCalcCoordinates = monitoring.getContaminationArea().getCoordinates();
        if (lastCalcCoordinates != null && lastCalcCoordinates.size() > 0) {
            currentPolygon = getNearestPrediction(monitoring);
            polygonDate = getPolygonDate(monitoring, currentPolygon);
        }
        return calculationService.getCalculationResult(monitoring, currentPolygon, polygonDate);
    }

    private Date getPolygonDate(EmergencyMonitoring monitoring, Polygon currentPolygon) {
        ContaminationArea contaminationArea = monitoring.getContaminationArea();
        LocalDateTime time = LocalDateTime.ofInstant(contaminationArea.getCreateDate().toInstant(),
                ZoneId.systemDefault());
        time.plus((contaminationArea.getPeriod() - 1) * contaminationArea.getCoordinates().indexOf(currentPolygon), ChronoUnit.MINUTES);
        return Date.from(time.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private Polygon getNearestPrediction(EmergencyMonitoring monitoring) {
        ContaminationArea contaminationArea = monitoring.getContaminationArea();
        List<Polygon> coordinates = contaminationArea.getCoordinates();
        Integer period = contaminationArea.getPeriod();

        LocalDateTime caCreateDate = LocalDateTime.ofInstant(contaminationArea.getCreateDate().toInstant(),
                ZoneId.systemDefault());
        LocalDateTime caTime = caCreateDate.plus(period * (coordinates.size() - 1), ChronoUnit.MINUTES);
        LocalDateTime currentDate = LocalDateTime.now();
        Duration minPeriod = Duration.between(currentDate, caTime);
        for (int i = coordinates.size() - 2; i >= 0; i--) {
            Duration currentDuration = Duration.between(currentDate, caCreateDate.plus(period * i, ChronoUnit.MINUTES));
            if (minPeriod.compareTo(currentDuration) < 0) {
                return coordinates.get(i + 1);
            } else {
                minPeriod = currentDuration;
            }
        }
        return coordinates.get(0);
    }

}
