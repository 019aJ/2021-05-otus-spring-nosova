package ru.otus.weatherforecast.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.otus.weatherforecast.models.CalculationInputData;
import ru.otus.weatherforecast.models.Forecast;

@Service
public class StormglassService {

    @Cacheable(value = "forecasts", key = "#calculationInputData.lat+#calculationInputData.lon+#currentDate")
    public Forecast askStormglass(CalculationInputData calculationInputData, String currentDate) {
        RestOperations rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "2101af9c-3e42-11ec-a2d8-0242ac130002-2101b000-3e42-11ec-a2d8-0242ac130002");
        HttpEntity entity = new HttpEntity(headers);

        String lat = calculationInputData.getLat().toString();
        String lng = calculationInputData.getLon().toString();
        ResponseEntity<Forecast> result = rest.exchange("https://api.stormglass.io/v2/weather/point?lat=" + lat + "&lng=" + lng + "&params=airTemperature,windSpeed,windDirection,waterTemperature", HttpMethod.GET, entity, Forecast.class);
        return result.getBody();
    }
}
