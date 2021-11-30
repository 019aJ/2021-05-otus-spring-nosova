package ru.otus.emergencymonitoringsystem.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.otus.emergencymonitoringsystem.dto.CalculationInputData;
import ru.otus.emergencymonitoringsystem.dto.CalculationResult;
import ru.otus.emergencymonitoringsystem.models.ContaminationArea;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculationService {

    public static final int DEFAULT_PERIOD = 60;
    @Qualifier("eurekaClient")
    private final EurekaClient eurekaClient;

    public CalculationResult getCalculationResult(EmergencyMonitoring emergencyMonitoring, Polygon currentPosition, Date positionTime) throws URISyntaxException {
        RestOperations rest = new RestTemplate();
        Application app = eurekaClient.getApplication("contamination-area-calculator");
        List<InstanceInfo> instances = app.getInstances();
        String baseUrl = String.format("%sapi/calculation/", instances.get(0).getHomePageUrl());

        URI uri = new URI(baseUrl);
        CalculationInputData calculationInputData = getCalculationInputData(emergencyMonitoring, currentPosition, positionTime);
        try {
            CalculationResult positions = rest.postForObject(uri, calculationInputData, CalculationResult.class);
            return positions;
        } catch (Exception e) {
            e.printStackTrace();
            ContaminationArea contaminationArea = emergencyMonitoring.getContaminationArea();
            CalculationResult result = new CalculationResult(contaminationArea != null ? contaminationArea.getCoordinates() : new ArrayList<>(), contaminationArea != null ? contaminationArea.getPeriod() : DEFAULT_PERIOD);
            return result;
        }
    }

    private CalculationInputData getCalculationInputData(EmergencyMonitoring emergencyMonitoring, Polygon currentPosition, Date positionTime) {
        CalculationInputData calculationInputData = new CalculationInputData();
        calculationInputData.setDensity(emergencyMonitoring.getMaterial().getDensity());
        calculationInputData.setWaterAreaLocation(emergencyMonitoring.getWaterArea().getCoordinates());
        calculationInputData.setCurrentPosition(currentPosition);
        calculationInputData.setPositionTime(positionTime);
        return calculationInputData;
    }
}
