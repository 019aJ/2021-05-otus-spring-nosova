package ru.otus.weatherforecast.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.weatherforecast.models.CalculationInputData;
import ru.otus.weatherforecast.models.CalculationResult;
import ru.otus.weatherforecast.models.Forecast;
import ru.otus.weatherforecast.models.ForecastDTO;
import ru.otus.weatherforecast.services.StormglassService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@AllArgsConstructor
public class TransformController {
    private final StormglassService stormglassService;

    @PostMapping("/api/weather-forecast")
    public CalculationResult forecast(@RequestBody CalculationInputData calculationInputData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Forecast forecast = stormglassService.askStormglass(calculationInputData, simpleDateFormat.format(new Date()));
        CalculationResult calculationResult = new CalculationResult(forecast.getHours().stream()
                .map(x -> new ForecastDTO(x.getTime(),
                        x.getAirTemperature().getNoaa(),
                        x.getWindDirection().getNoaa(),
                        x.getWindSpeed().getNoaa(),
                        x.getWaterTemperature().getNoaa())).collect(Collectors.toList()));
        return calculationResult;
    }


}
