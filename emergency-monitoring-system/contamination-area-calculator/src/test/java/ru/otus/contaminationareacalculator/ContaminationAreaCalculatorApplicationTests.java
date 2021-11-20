package ru.otus.contaminationareacalculator;

import com.netflix.discovery.EurekaClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.http.HttpHeaders;
import ru.otus.contaminationareacalculator.controllers.CalculationController;
import ru.otus.contaminationareacalculator.models.CalculationInputData;
import ru.otus.contaminationareacalculator.models.CalculationResult;
import ru.otus.contaminationareacalculator.models.ForecastDTO;
import ru.otus.contaminationareacalculator.models.ForecastResult;
import ru.otus.contaminationareacalculator.services.CoordTransformationService;
import ru.otus.contaminationareacalculator.services.IntersectionService;
import ru.otus.contaminationareacalculator.services.WeatherForecastService;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Тесты расчета перемещения пятна")
class ContaminationAreaCalculatorApplicationTests {
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Температура воды")
    public void waterTempInfluence() throws Exception {
        Point p1 = calcOnePoint(5, 0, 10, 10);
        Double waterTemperatureKoef1 = (5 - 4) / 5D;

        Point p2 = calcOnePoint(10, 0, 10, 10);
        Double waterTemperatureKoef2 = (10 - 4) / 10D;

        Double ratio = waterTemperatureKoef1 / waterTemperatureKoef2;
        assertEquals(ratio, p1.getX() / p2.getX(), 0.00001);
        assertEquals(ratio, p1.getY() / p2.getY(), 0.00001);
    }

    @Test
    @DisplayName("Температура воздуха")
    public void airTempInfluence() throws Exception {
        Point p1 = calcOnePoint(10, 0, 10, 5);
        Double airTemperatureKoef1 = (5 - 3) / 5D;

        Point p2 = calcOnePoint(10, 0, 10, 10);
        Double airTemperatureKoef2 = (10 - 3) / 10D;

        Double ratio = airTemperatureKoef1 / airTemperatureKoef2;
        assertEquals(ratio, p1.getX() / p2.getX(), 0.00001);
        assertEquals(ratio, p1.getY() / p2.getY(), 0.00001);
    }

    @Test
    @DisplayName("Скорость ветра")
    public void windSpeedInfluence() throws Exception {
        Point p1 = calcOnePoint(10, 0, 5, 10);
        Double windKoef1 = 5 * 60 * 60d;

        Point p2 = calcOnePoint(10, 0, 10, 10);
        Double windKoef2 = 10 * 60 * 60d;

        Double ratio = windKoef1 / windKoef2;
        assertEquals(ratio, p1.getX() / p2.getX(), 0.00001);
        assertEquals(ratio, p1.getY() / p2.getY(), 0.00001);
    }

    private Point calcOnePoint(double waterTemperature, double windDirection, double windSpeed, double airTemperature) throws URISyntaxException {
        CoordTransformationService coordTransformationService = coordTransformationService();
        CalculationInputData calculationInputData = сalculationInputData();
        WeatherForecastService weatherForecastService = weatherForecastService(waterTemperature, windDirection, windSpeed, airTemperature);
        CalculationResult res = new CalculationController(weatherForecastService,
                coordTransformationService, new IntersectionService(), 1).calculation(calculationInputData);
        return res.getPositions().get(1).getPoints().get(0);
    }

    private CalculationInputData сalculationInputData() {
        double[][] coords = new double[][]{{0, 0}, {10000000, 10000000}, {11000000, 11000000}};
        Polygon poly1 = new Polygon(Arrays.asList(coords).stream().map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
        LocalDateTime currentDate = LocalDateTime.now();
        Instant date = currentDate.withHour(1).atZone(ZoneId.systemDefault())
                .toInstant();
        return new CalculationInputData(poly1, 1d, null, Date.from(date));
    }

    private CoordTransformationService coordTransformationService() {
        CoordTransformationService coordTransformationService = new CoordTransformationService(Mockito.mock(EurekaClient.class)) {
            @Override
            public List<Polygon> getCoordInMeters(List<Polygon> geometries) throws URISyntaxException {
                return geometries;
            }

            @Override
            public List<Polygon> getCoordInGrads(List<Polygon> geometries) throws URISyntaxException {
                return geometries;
            }
        };
        return coordTransformationService;
    }

    private WeatherForecastService weatherForecastService(double waterTemperature, double windDirection, double windSpeed, double airTemperature) {
        WeatherForecastService weatherForecastService = new WeatherForecastService(Mockito.mock(EurekaClient.class)) {
            @Override
            public ForecastResult weatherForecast(Double lon, Double lat) throws URISyntaxException {
                LocalDateTime currentDate = LocalDateTime.now();
                List<ForecastDTO> list = IntStream.range(0, 24).mapToObj(i ->
                        {
                            Instant date = currentDate.withHour(i).atZone(ZoneId.systemDefault())
                                    .toInstant();
                            return new ForecastDTO(Date.from(date), waterTemperature, windDirection, windSpeed, airTemperature);
                        }
                ).collect(Collectors.toList());
                return new ForecastResult(list);
            }
        };
        return weatherForecastService;
    }

}
