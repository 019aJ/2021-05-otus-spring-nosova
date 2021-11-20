package ru.otus.contaminationareacalculator.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.contaminationareacalculator.models.CalculationInputData;
import ru.otus.contaminationareacalculator.models.CalculationResult;
import ru.otus.contaminationareacalculator.models.ForecastDTO;
import ru.otus.contaminationareacalculator.models.ForecastResult;
import ru.otus.contaminationareacalculator.services.CoordTransformationService;
import ru.otus.contaminationareacalculator.services.IntersectionService;
import ru.otus.contaminationareacalculator.services.WeatherForecastService;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@PropertySource("classpath:application.yml")
@ComponentScan
public class CalculationController {
    public static final int DEFAULT_PERIOD_MINUTES = 60;

    private final Integer predictionCount;
    private final WeatherForecastService weatherForecastService;
    private final CoordTransformationService coordTransformationService;
    private final IntersectionService intersectionService;

    public CalculationController(WeatherForecastService weatherForecastService, CoordTransformationService coordTransformationService, IntersectionService intersectionService, @Value("${predictionCount:4}") Integer predictionCount) {
        this.predictionCount = predictionCount;
        this.weatherForecastService = weatherForecastService;
        this.coordTransformationService = coordTransformationService;
        this.intersectionService = intersectionService;
    }

    @PostMapping("/api/calculation")
    public CalculationResult calculation(@RequestBody CalculationInputData calculationInputData) throws URISyntaxException {
        List<Polygon> positions = new ArrayList<>();
        //Переводим координаты пятна в метры, считать в градусах неудобно
        List<Polygon> inMeters = coordInMeters(calculationInputData);
        Polygon prevPos = inMeters.get(0);
        Polygon seaBounds = inMeters.get(1);
        //Получаем прогноз погоды
        Point point = calculationInputData.getCurrentPosition().getPoints().get(0);
        ForecastResult forecast = weatherForecast(point.getX(), point.getY());

        positions.add(calculationInputData.getCurrentPosition());

        for (int i = 0; i < predictionCount; i++) {
            prevPos = nextPosition(prevPos, seaBounds, calculationInputData.getDensity(), calculationInputData.getPositionTime(), forecast, i + 1);
            /*Если пятно попало в море, то добавляем в прогноз. Если вылезло на сушу, то это уже перебор, ничего не добавляем*/
            intersectionService.checkIntersection(seaBounds, prevPos).ifPresent(p -> {
                try {
                    positions.add(coordInGrads(p).get(0));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        }
        CalculationResult calculationResult = new CalculationResult();
        calculationResult.setPositions(positions);
        calculationResult.setPeriod(DEFAULT_PERIOD_MINUTES);
        return calculationResult;
    }

    private List<Polygon> coordInGrads(Polygon prevPos) throws URISyntaxException {
        return coordTransformationService.getCoordInGrads(Arrays.asList(prevPos));
    }

    private List<Polygon> coordInMeters(CalculationInputData calculationInputData) throws URISyntaxException {
        return coordTransformationService.getCoordInMeters(Arrays.asList(calculationInputData.getCurrentPosition(), calculationInputData.getWaterAreaLocation()));
    }

    private Polygon nextPosition(Polygon area, Polygon seaBounds, Double density,
                                 Date positionTime, ForecastResult forecast, int index) {
        Double[] shift = shiftValue(density, forecast, index);
        return new Polygon(area.getPoints().stream()
                .map(p -> {
                    Point point = new Point(p.getX() + shift[0], p.getY() + shift[1]);
                    return point;
                }).collect(Collectors.toList()));
    }

    private void checkGround(Polygon seaBounds, Polygon polygon) {

    }

    private Double[] shiftValue(Double density, ForecastResult forecast, int index) {
        LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC).plus(index * DEFAULT_PERIOD_MINUTES, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.HOURS);
        ForecastDTO forecastByHour = forecast.getForecastByHours().stream().filter(x -> LocalDateTime.ofInstant(x.getTime().toInstant(), ZoneOffset.UTC).truncatedTo(ChronoUnit.HOURS).isEqual(date)).findAny().orElse(forecast.getForecastByHours().get(0));
        //0° indicates wind coming from north - прибавляем 90 чтоб было нормальное направление
        Double directionAngleRad = forecastByHour.getWindDirection() * Math.PI / 180 + Math.PI / 2;
        Double vx = Math.cos(directionAngleRad);
        Double vy = Math.sin(directionAngleRad);
        //скорость в м/с
        Double distance = forecastByHour.getWindSpeed() * DEFAULT_PERIOD_MINUTES * 60;

        //Наверное скорость зависит от плотности нефти: пусть чем больше плотность, тем медленнее перемещение
        Double densityKoef = density * 0.98;
        //Скорость движения пятна меньше скорости ветра, пусть на четверть
        Double baseOilKoef = 0.25;
        //Температура воды тоже как-то влияет, пусть так
        Double waterTemperatureKoef = forecastByHour.getWaterTemperature() != 0 && forecastByHour.getWaterTemperature() != 4 ? (forecastByHour.getWaterTemperature() - 4) / forecastByHour.getWaterTemperature() : 1;
        //И температура воздуха допустим так
        Double airTemperatureKoef = forecastByHour.getAirTemperature() != 0 && forecastByHour.getAirTemperature() != 3 ? (forecastByHour.getAirTemperature() - 3) / forecastByHour.getAirTemperature() : 1;
        //Окончательный коэфициент будет такой
        Double koef = densityKoef * baseOilKoef * waterTemperatureKoef * airTemperatureKoef;
        //если безветренно, то неинтересно=)
        if (koef < 0.02) {
            koef = 0.02;
        }
        return new Double[]{vx * distance * koef, vy * distance * koef};
    }

    private ForecastResult weatherForecast(Double lon, Double lat) throws URISyntaxException {
        return weatherForecastService.weatherForecast(lon, lat);
    }
}
