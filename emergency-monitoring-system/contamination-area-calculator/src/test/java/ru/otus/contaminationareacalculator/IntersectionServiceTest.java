package ru.otus.contaminationareacalculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.http.HttpHeaders;
import ru.otus.contaminationareacalculator.services.IntersectionService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Тесты пересечения пятна с сушей")
public class IntersectionServiceTest {
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    @Autowired
    private IntersectionService intersectionService;
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Пятно целиком в море")
    public void wholeInSea() throws Exception {
        Polygon p1 = getPoints(new double[][]{
                {0, 0}, {0, 100}, {100, 100}, {100, 0}, {0, 0}
        });
        Polygon p2 = getPoints(
                new double[][]{
                        {25, 25}, {25, 50}, {50, 50}, {50, 25}, {25, 25}}
        );
        Optional<Polygon> result = intersectionService.checkIntersection(p1, p2);
        Polygon poly = result.orElseThrow();
        Set<Point> set1 = new HashSet(p2.getPoints());
        Set<Point> set2 = new HashSet(poly.getPoints());

        assertEquals(set1.equals(set2), true);
    }

    @Test
    @DisplayName("Пятно целиком в море2")
    public void wholeInSea2() throws Exception {
        Polygon p1 = seaCoords();
        Polygon p2 = getPoints(
                new double[][]
                        {{15992308, 6694622}, {15992255, 6694467}, {15992100, 6694360}, {15991854, 6694355}, {15991670, 6694474}, {15991548, 6694606}, {15991513, 6694814}, {15991630, 6694969}, {15991768, 6695036}, {15991849, 6695057}, {15992031, 6695055}, {15992193, 6694950}, {15992313, 6694783}, {15992308, 6694622}}
        );
        Optional<Polygon> result = intersectionService.checkIntersection(p1, p2);
        Polygon poly = result.orElseThrow();
        Set<Point> set1 = new HashSet(p2.getPoints());
        Set<Point> set2 = new HashSet(poly.getPoints());

        assertEquals(set1.equals(set2), true);
    }

    @Test
    @DisplayName("Пятно целиком не в море")
    public void wholeNotInSea() throws Exception {
        Polygon p1 = seaCoords();
        Polygon p2 = getPoints(
                new double[][]{{-15992307.883396288, -6694622.4673028765}, {-15992255.340596637, -6694467.190655538}, {-15992100.161226468, -6694359.74808439}, {-15991854.033832327, -6694354.929264678}, {15991670.134033537, 6694474.329746932}, {-15991548.350510608, -6694605.690143427}, {-15991512.50563457, -6694813.622267937}, {-15991629.502419394, -6694968.905507984}, {-15991768.095185433, -6695035.66036082}, {-15992307.883396288, -6694622.4673028765}});
        Optional<Polygon> result = intersectionService.checkIntersection(p1, p2);
        assertEquals(result.isEmpty(), true);
    }

    private Polygon seaCoords() {
        double[][] coords = new double[][]{
                {15992308, 6694622}, {15992255, 6694467}, {15992100, 6694360}, {15991854, 6694355}, {15991670, 6694474}, {15991548, 6694606}, {15991513, 6694814}, {15991630, 6694969}, {15991768, 6695036}, {15991849, 6695057}, {15992031, 6695055}, {15992193, 6694950}, {15992313, 6694783}, {15992308, 6694622}
        };
        return getPoints(coords);

    }

    private Polygon getPoints(double[][] coords) {
        return new Polygon(Arrays.asList(coords).stream().map(x -> new Point(x[0], x[1])).collect(Collectors.toList()));
    }
}
