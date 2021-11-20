package ru.otus.coordtransform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.otus.coordtransform.models.CalculationInputData;
import ru.otus.coordtransform.models.CalculationResult;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Тесты преобразования координат")
class CoordTransformApplicationTests {
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Метры в градусы")
    public void meterToGrad() throws Exception {
        CalculationInputData calculationInputData = new CalculationInputData();
        double[][] coords = new double[][]{{15645938.29, 6067155.65}, {15646607.10, 6066663.67}, {15646564.13, 6065607.77},
                {15645536.98, 6065330.62}, {15644925.50, 6066094.72}, {15645169.18, 6066845.16}, {15645938.29, 6067155.65}};
        Polygon poly1 = new Polygon(Arrays.asList(coords).stream().map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));

        calculationInputData.setGeometry(Arrays.asList(poly1));
        calculationInputData.setTo("EPSG:4326");
        calculationInputData.setFrom("EPSG:3857");
        HttpEntity<CalculationInputData> entity = new HttpEntity<>(calculationInputData, headers);

        ResponseEntity<CalculationResult> response = restTemplate.exchange(
                createURLWithPort("/api/transformation"),
                HttpMethod.POST, entity, CalculationResult.class);

        List<Double> coordsRes = Arrays.asList(140.54985, 47.76082, 140.55586, 47.75784, 140.55547, 47.75147, 140.54625, 47.74979, 140.54075, 47.75441, 140.54294, 47.75894, 140.54985, 47.76082);
        List<Double> res = response.getBody().getGeometry().stream().flatMap(poly -> poly.getPoints().stream()).flatMap(p -> Arrays.asList(p.getX(), p.getY()).stream()).collect(Collectors.toList());
		assertEquals(coordsRes.size(), res.size());

		for (int i = 0; i < coordsRes.size(); i++) {
			assertEquals(coordsRes.get(i), res.get(i), 0.00001);
		}
    }


    @Test
    @DisplayName("градусы в метры")
    public void gradToMeters() throws Exception {
        CalculationInputData calculationInputData = new CalculationInputData();
        double[][] coords = new double[][]{{140.549855, 47.76082}, {140.555863, 47.757849}, {140.555477, 47.751472},
                {140.54625, 47.749798}, {140.540757, 47.754413}, {140.542946, 47.758945}, {140.549855, 47.76082}};
        Polygon poly1 = new Polygon(Arrays.asList(coords).stream().map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
        calculationInputData.setGeometry(Arrays.asList(poly1));
        calculationInputData.setFrom("EPSG:4326");
        calculationInputData.setTo("EPSG:3857");
        HttpEntity<CalculationInputData> entity = new HttpEntity<>(calculationInputData, headers);

        ResponseEntity<CalculationResult> response = restTemplate.exchange(
                createURLWithPort("/api/transformation"),
                HttpMethod.POST, entity, CalculationResult.class);
        List<Double> coordsRes = Arrays.asList(15645938.29, 6067155.65,15646607.10, 6066663.67,15646564.13, 6065607.77,15645536.98, 6065330.62,15644925.50, 6066094.72,15645169.18, 6066845.16,15645938.29, 6067155.65);
        List<Double> res = response.getBody().getGeometry().stream().flatMap(poly -> poly.getPoints().stream()).flatMap(p -> Arrays.asList(p.getX(), p.getY()).stream()).collect(Collectors.toList());

        assertEquals(coordsRes.size(), res.size());

        for (int i = 0; i < coordsRes.size(); i++) {
            assertEquals(coordsRes.get(i), res.get(i), 1);
        }
    }

    private Polygon seaCoords() {
        double[][] coords = new double[][]
                { { 143.661346, 51.412698 }, { 143.660874, 51.411828 }, { 143.65948, 51.411226 }, { 143.657269, 51.411199 }, { 143.655617, 51.411868 }, { 143.654523, 51.412604 }, { 143.654201, 51.413769 }, { 143.655252, 51.414639 }, { 143.656497, 51.415013 }, { 143.657227, 51.415134 }, { 143.658857, 51.41512 }, { 143.660316, 51.414531 }, { 143.661389, 51.413595 }, { 143.661346, 51.412698 } }
                ;
        return new Polygon(Arrays.asList(coords).stream().map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
    }
    private Polygon polyCoords() {
        double[][] coords = new double[][]
                {{143.661346, 51.412698}, {143.660874, 51.411828}, {143.65948, 51.411226}, {143.657269, 51.411199}, {143.655617, 51.411868}, {143.654523, 51.412604}, {143.654201, 51.413769}, {143.655252, 51.414639}, {143.656497, 51.415013}, {143.657227, 51.415134}, {143.658857, 51.41512}, {143.660316, 51.414531}, {143.661389, 51.413595}, {143.661346, 51.412698}};
        return new Polygon(Arrays.asList(coords).stream().map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
    }

    @Test
    public void test(){
        Polygon sc = polyCoords();
        CalculationInputData calculationInputData = new CalculationInputData();
        calculationInputData.setGeometry(Arrays.asList(sc));
        calculationInputData.setFrom("EPSG:4326");
        calculationInputData.setTo("EPSG:3857");
        HttpEntity<CalculationInputData> entity = new HttpEntity<>(calculationInputData, headers);

        ResponseEntity<CalculationResult> response = restTemplate.exchange(
                createURLWithPort("/api/transformation"),
                HttpMethod.POST, entity, CalculationResult.class);
        List<Double> res = response.getBody().getGeometry().stream().flatMap(poly -> poly.getPoints().stream()).flatMap(p -> Arrays.asList(p.getX(), p.getY()).stream()).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        for (int i = 0; i < res.size(); i+=2) {
            sb.append("{");
            sb.append(df.format(Math.round(res.get(i))));
            sb.append(", ");
            sb.append(df.format(Math.round(res.get(i+1))));
            sb.append("},");
        }
        System.out.println(sb.toString());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
