package ru.otus.contaminationareacalculator.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.otus.contaminationareacalculator.models.TransformInputData;
import ru.otus.contaminationareacalculator.models.TransformResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@AllArgsConstructor
public class CoordTransformationService {
    public static final String EPSG_GRAD = "EPSG:4326";
    public static final String EPSG_METER = "EPSG:3857";
    @Qualifier("eurekaClient")
    private final EurekaClient eurekaClient;

    public List<Polygon> getCoordInMeters(List<Polygon> geometries) throws URISyntaxException {
        return transform(geometries, EPSG_GRAD, EPSG_METER);
    }

    public List<Polygon> getCoordInGrads(List<Polygon> geometries) throws URISyntaxException {
        return transform(geometries, EPSG_METER, EPSG_GRAD);
    }

    private List<Polygon> transform(List<Polygon> geometries, String from, String to) throws URISyntaxException {
        RestOperations rest = new RestTemplate();
        Application app = eurekaClient.getApplication("coord-transform");
        List<InstanceInfo> instances = app.getInstances();
        String baseUrl = String.format("%sapi/transformation/", instances.get(0).getHomePageUrl());

        URI uri = new URI(baseUrl);
        TransformInputData inputData = new TransformInputData(geometries, from, to);
        TransformResult positions = rest.postForObject(uri, inputData, TransformResult.class);
        return positions.getGeometry();
    }
}
