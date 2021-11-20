package ru.otus.contaminationareacalculator.services;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.otus.contaminationareacalculator.models.ForecastDTO;
import ru.otus.contaminationareacalculator.models.ForecastInputData;
import ru.otus.contaminationareacalculator.models.ForecastResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherForecastService {
    @Qualifier("eurekaClient")
    private final EurekaClient eurekaClient;

    @HystrixCommand(fallbackMethod = "defaultWeatherForecast")
    public ForecastResult weatherForecast(Double lon, Double lat) throws URISyntaxException {
        RestOperations rest = new RestTemplate();

        Application app = eurekaClient.getApplication("weather-forecast");
        List<InstanceInfo> instances = app.getInstances();
        String baseUrl = String.format("%sapi/weather-forecast/", instances.get(0).getHomePageUrl());

        URI uri = new URI(baseUrl);
        ForecastInputData inputData = new ForecastInputData(lon, lat);
        ForecastResult forecast = rest.postForObject(uri, inputData, ForecastResult.class);
        return forecast;
    }

    private ForecastResult defaultWeatherForecast(Double lon, Double lat) {
        ForecastDTO forecastDTO = new ForecastDTO(new Date(), 7d, 15d, 15d, 7d);
        ForecastResult forecast = new ForecastResult(Arrays.asList(forecastDTO));
        return forecast;
    }
}
