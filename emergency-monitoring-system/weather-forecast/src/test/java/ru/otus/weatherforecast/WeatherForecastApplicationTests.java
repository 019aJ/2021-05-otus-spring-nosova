package ru.otus.weatherforecast;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import ru.otus.weatherforecast.models.CalculationInputData;
import ru.otus.weatherforecast.models.Forecast;
import ru.otus.weatherforecast.services.StormglassService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Тест запроса прогноза погоды")
class WeatherForecastApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StormglassService intersectionService;

    @Test
    @DisplayName("Запрос прогноза")
    public void forecastTest() {
        CalculationInputData data = new CalculationInputData();
        data.setLat(10d);
        data.setLon(10d);
        String currentDate = new Date().toString();
        Forecast forecast = intersectionService.askStormglass(data, currentDate);
        assertNotNull(forecast.getHours().get(0).getWaterTemperature());
    }
}
